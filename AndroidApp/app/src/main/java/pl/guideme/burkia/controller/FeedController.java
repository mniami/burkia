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

package pl.guideme.burkia.controller;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.UUID;
import de.greenrobot.event.EventBus;
import pl.guideme.burkia.R;
import pl.guideme.burkia.compat.NotificationManagerCompatProvider;
import pl.guideme.burkia.config.DemoConfig;
import pl.guideme.burkia.event.SubscriberPriority;
import pl.guideme.burkia.event.post.DeletePostEvent;
import pl.guideme.burkia.eventbus.EventBusProvider;
import pl.guideme.burkia.job.JobManagerProvider;

@EBean
public class FeedController {
    @Bean
    protected JobManagerProvider mJobManager;
    @Bean
    protected DemoConfig mDemoConfig;
    @Bean
    protected EventBusProvider mEventBusProvider;
    @RootContext
    protected Context mAppContext;
    protected NotificationManagerCompatProvider mNotificationManagerCompatProvider;

    public FeedController() {
        mEventBusProvider.get().register(this, SubscriberPriority.LOW);
    }

//    public void onEventMainThread(DeletePostEvent event) {
//        if (event.didNotifyUser() || !event.isSyncFailure()) {
//            return;
//        }
//        Intent intent = FeedActivity.intentForSendPost(mAppContext, event.getText());
//        PendingIntent pendingIntent = PendingIntent.getActivity(mAppContext,
//                0, intent, 0);
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(mAppContext)
//                .setSmallIcon(R.drawable.ic_action_backup)
//                .setContentTitle(mAppContext.getString(R.string.cannot_sync_post, event.getText()))
//                .setAutoCancel(true)
//                .setContentIntent(pendingIntent);
//        mNotificationManagerCompat.get().notify(1, builder.build());
//    }
//
//    public void fetchFeedAsync(boolean fromUI, @Nullable Long userId) {
//        mJobManager.addJobInBackground(
//                new FetchFeedJob(fromUI ? BaseJob.UI_HIGH : BaseJob.BACKGROUND, userId));
//    }
//
//    public void sendPostAsync(String text) {
//        mJobManager.addJobInBackground(new SaveNewPostJob(text, UUID.randomUUID().toString(),
//                mDemoConfig.getUserId()));
//    }
}
