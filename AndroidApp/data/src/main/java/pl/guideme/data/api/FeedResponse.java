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

package pl.guideme.data.api;

import java.util.List;

import pl.guideme.data.vo.Atomic;
import pl.guideme.data.vo.Post;
import pl.guideme.data.vo.User;

public class FeedResponse {

    private List<Post> mPosts;
    private List<User> mUsers;
    private List<Atomic> mAtomics;

    public List<Post> getPosts() {
        return mPosts;
    }

    public void setPosts(List<Post> posts) {
        this.mPosts = posts;
    }

    public List<User> getUsers() {
        return mUsers;
    }

    public void setUsers(List<User> users) {
        this.mUsers = users;
    }

    public List<Atomic> getAtomics() {
        return mAtomics;
    }

    public void setAtomics(List<Atomic> atomics) {
        mAtomics = atomics;
    }
}
