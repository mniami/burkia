/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package pl.guideme.burkia.job.post;

import android.content.Context;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import pl.guideme.burkia.AppConfigProvider;
import pl.guideme.burkia.api.ApiService;
import pl.guideme.burkia.api.NewPostResponse;
import pl.guideme.burkia.event.post.DeletePostEvent;
import pl.guideme.burkia.event.post.NewPostEvent;
import pl.guideme.burkia.event.post.UpdatedPostEvent;
import pl.guideme.burkia.eventbus.EventBusProvider;
import pl.guideme.burkia.job.BaseJob;
import pl.guideme.burkia.job.NetworkException;
import pl.guideme.burkia.model.FeedModel;
import pl.guideme.burkia.model.PostModel;
import pl.guideme.burkia.model.UserModel;
import pl.guideme.burkia.util.L;
import pl.guideme.burkia.vo.Post;
import retrofit.Response;

@EBean
public class SaveNewPostJob extends BaseJob {

    private static final String GROUP = "new_post";

    private String mText;
    private String mClientId;
    private long mUserId;

    @Bean
    protected ApiService mApiService;
    @Bean
    protected EventBusProvider mEventBus;
    @Bean
    protected FeedModel mFeedModel;
    @Bean
    protected PostModel mPostModel;
    @Bean
    protected transient UserModel mUserModel;
    @Bean
    protected transient AppConfigProvider appConfigProvider;

    private BaseJob job;

    public void initialize(Context context, String text, String clientId, long userId){
        mText = text;
        mClientId = clientId;
        mUserId = userId;
    }

    @Override
    protected void onAdded() {
        Post post = new Post();
        post.setText(mText);
        post.setId(mFeedModel.generateIdForNewLocalPost());
        post.setClientId(mClientId);
        post.setUserId(mUserId);
        post.setPending(true);
        // make sure whatever time we put here is greater / eq to last known time in database.
        // this will work around issues related to client's time.
        // this time is temporary anyways as it will be overriden when it is synched to server
        long feedTs = mFeedModel.getLatestTimestamp(null);
        long now = System.currentTimeMillis();
        post.setCreated(Math.max(feedTs, now) + 1);
        L.d("assigned timestamp %s to the post", post.getCreated());
        mPostModel.save(post);
        mEventBus.get().post(new NewPostEvent(post));
    }

    @Override
    protected void onRun() throws Throwable {
        Post post = mPostModel.loadByClientIdAndUserId(mClientId, mUserId);
        if (post != null && !post.isPending()) {
            // looks like post probably arrived from somewhere else. Good Job!
            mEventBus.get().post(new UpdatedPostEvent(post));
            return;
        }
        Response<NewPostResponse> response = mApiService.sendPost(mText, mClientId, mUserId)
                .execute();
        if (response.isSuccess()) {
            NewPostResponse body = response.body();
            body.getPost().setPending(false);
            mPostModel.save(body.getPost());
            mUserModel.save(body.getUser());
            mEventBus.get().post(new UpdatedPostEvent(body.getPost()));
        } else {
            throw new NetworkException(response.code());
        }
    }

    @Override
    protected int getRetryLimit() {
        return appConfigProvider.getConfig().getNewPostRetryCount();
    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(Throwable throwable, int runCount,
            int maxRunCount) {
        if (shouldRetry(throwable)) {
            // For the purposes of the demo, just back off 250 ms.
            RetryConstraint constraint = RetryConstraint
                    .createExponentialBackoff(runCount, 250);
            constraint.setApplyNewDelayToGroup(true);
            return constraint;
        }
        return RetryConstraint.CANCEL;
    }

    @Override
    protected void onCancel() {
        Post post = mPostModel.loadByClientIdAndUserId(mClientId, mUserId);
        if (post != null) {
            mPostModel.delete(post);
        }
        mEventBus.get().post(new DeletePostEvent(true, mText, post));
    }
}
