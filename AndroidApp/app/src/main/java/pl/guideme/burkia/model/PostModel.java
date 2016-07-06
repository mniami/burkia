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

import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import com.raizlabs.android.dbflow.runtime.TransactionManager;
import com.raizlabs.android.dbflow.sql.builder.Condition;
import com.raizlabs.android.dbflow.sql.language.Select;

import org.androidannotations.annotations.EBean;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

import pl.guideme.burkia.App;
import pl.guideme.burkia.util.ValidationUtil;
import pl.guideme.burkia.vo.Post;
import pl.guideme.burkia.vo.Post$Table;

@EBean(scope = EBean.Scope.Singleton)
public class PostModel extends BaseModel {

    public List<Post> loadPostsSince(long since) {
        return new Select().from(Post.class).where(
                Condition.column(Post$Table.MCREATED).greaterThan(since)
        ).queryList();
    }

    public List<Post> loadPostsOfUser(long userId, long since) {
        return new Select().from(Post.class).where(
                Condition.column(Post$Table.MCREATED).greaterThan(since)
        ).and(Condition.column(Post$Table.MUSERID).eq(userId)).queryList();
    }

    public Post load(long id) {
        return new Select().from(Post.class)
                .where(Condition.column(Post$Table.MID).eq(id)).querySingle();
    }

    public synchronized void save(Post post) {
        ValidationUtil.validate(post);
        saveValid(post);
    }

    public synchronized void saveAll(final List<Post> posts) {
        ValidationUtil.pruneInvalid(posts);
        if (posts.isEmpty()) {
            return;
        }
        TransactionManager.transact(mSQLiteDatabase, new Runnable() {
            @Override
            public void run() {
                for (Post post : posts) {
                    saveValid(post);
                }
            }
        });
    }

    private void saveValid(Post post) {
        Post existing = loadByClientIdAndUserId(post.getClientId(), post.getUserId());
        if (existing == null) {
            post.save();
        } else {
            post.setId(existing.getId());
            post.update();
        }
    }

    @Nullable
    public synchronized Post loadByClientIdAndUserId(String clientId, long userId) {
        if (StringUtils.isEmpty(clientId)) {
            return null;
        }
        return new Select().from(Post.class)
                .where(Condition.column(Post$Table.MCLIENTID).eq(clientId))
                .and(Condition.column(Post$Table.MUSERID).eq(userId))
                .querySingle();
    }

    public void delete(Post post) {
        post.delete();
    }
}
