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

package pl.guideme.burkia.job.feed;

import android.content.Context;
import android.support.annotation.Nullable;

import de.greenrobot.event.EventBus;
import pl.guideme.burkia.api.ApiService;
import pl.guideme.burkia.api.FeedResponse;
import pl.guideme.burkia.event.feed.FetchedFeedEvent;
import pl.guideme.burkia.job.BaseJob;
import pl.guideme.burkia.job.NetworkException;
import pl.guideme.burkia.job.post.RetryConstraint;
import pl.guideme.burkia.model.FeedModel;
import pl.guideme.burkia.model.PostModel;
import pl.guideme.burkia.model.UserModel;
import pl.guideme.burkia.vo.Post;
import retrofit.Call;
import retrofit.Response;

public class FetchFeedJob extends BaseJob {
    transient FeedModel mFeedModel;
    transient EventBus mEventBus;
    transient UserModel mUserModel;
    transient PostModel mPostModel;
    transient ApiService mApiService;

    private static final String GROUP = "FetchFeedJob";
    private Long mUserId;

    public void initialize(@Nullable Long userId) {
        mUserId = userId;
    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        long timestamp = mFeedModel.getLatestTimestamp(mUserId);
        final Call<FeedResponse> feed;
        if (mUserId == null) {
            feed = mApiService.feed(timestamp);
        } else {
            feed = mApiService.userFeed(mUserId, timestamp);
        }
        Response<FeedResponse> response = feed.execute();
        if (response.isSuccess()) {
            Post oldest = handleResponse(response.body());
            mEventBus.post(new FetchedFeedEvent(true, mUserId, oldest));
        } else {
            throw new NetworkException(response.code());
        }
    }

    @Nullable
    private Post handleResponse(FeedResponse body) {
        // We could put these two into a transaction but it is OK to save a user even if we could
        // not save their post so we don't care.
        if (body.getUsers() != null) {
            mUserModel.saveAll(body.getUsers());
        }
        Post oldest = null;
        if (body.getPosts() != null) {
            mPostModel.saveAll(body.getPosts());
            long since = 0;
            for (Post post : body.getPosts()) {
                if (post.getCreated() > since) {
                    since = post.getCreated();
                }
                if (oldest == null || oldest.getCreated() > post.getCreated()) {
                    oldest = post;
                }
            }
            if (since > 0) {
                mFeedModel.saveFeedTimestamp(since, mUserId);
            }
        }
        return oldest;
    }

    @Override
    public RetryConstraint shouldReRunOnThrowable(Throwable throwable, int runCount,
                                                  int maxRunCount) {
        if (shouldRetry(throwable)) {
            return RetryConstraint.createExponentialBackoff(runCount, 1000);
        }
        return RetryConstraint.CANCEL;
    }

    @Override
    protected int getRetryLimit() {
        return 2;
    }

    @Override
    protected void onCancel() {
        mEventBus.post(new FetchedFeedEvent(false, mUserId, null));
    }
}
