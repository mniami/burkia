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

package pl.guideme.burkia.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pl.guideme.burkia.vo.FeedItem;
import pl.guideme.burkia.vo.Post;
import pl.guideme.burkia.vo.User;

@EBean(scope = EBean.Scope.Singleton)
public class FeedModel extends BaseModel {

    private static final String PREF_NAME = "feed_pref";
    private static final String KEY_LAST_FEED_TIMESTAMP = "timestamp";
    private static final String KEY_LOCAL_POST_ID = "local_post_id";
    private SharedPreferences mPrefs;

    @Bean
    protected UserModel mUserModel;
    @Bean
    protected PostModel mPostModel;
    @RootContext
    protected Context mAppContext;

    public void saveFeedTimestamp(long timestamp, @Nullable Long userId) {
        getPref().edit().putLong(createUserTimestampKey(userId), timestamp).commit();
    }

    public List<FeedItem> loadFeed(long since, @Nullable Long userId) {
        final List<Post> posts;
        if (userId == null) {
            posts = mPostModel.loadPostsSince(since);
        } else {
            posts = mPostModel.loadPostsOfUser(userId, since);
        }
        List<Long> userIds = new ArrayList<>();
        for (Post post : posts) {
            userIds.add(post.getUserId());
        }
        Map<Long, User> users = mUserModel.loadUsersAsMap(userIds);
        List<FeedItem> result = new ArrayList<>();
        for (Post post : posts) {
            User user = users.get(post.getUserId());
            if (user != null) {
                result.add(new FeedItem(post, user));
            }
        }
        return result;
    }

    public long getLatestTimestamp(@Nullable Long userId) {
        return getPref().getLong(createUserTimestampKey(userId), 0);
    }

    public synchronized long generateIdForNewLocalPost() {
        long id = getPref().getLong(KEY_LOCAL_POST_ID, Long.MIN_VALUE);
        getPref().edit().putLong(KEY_LOCAL_POST_ID, id + 1).commit();
        return id;
    }

    private SharedPreferences getPref() {
        if (mPrefs == null) {
            mPrefs = mAppContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        }
        return mPrefs;
    }

    private static String createUserTimestampKey(@Nullable Long userId) {
        if (userId == null) {
            return KEY_LAST_FEED_TIMESTAMP;
        }
        return KEY_LAST_FEED_TIMESTAMP + "_" + userId;
    }

    public void clear() {
        getPref().edit().clear().commit();
    }
}
