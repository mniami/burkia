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

package pl.guideme.data.vo;

import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.StringUtils;

import pl.guideme.data.util.DateUtil;
import pl.guideme.data.util.Validation;
import pl.guideme.data.util.ValidationFailedException;

public class Post implements Validation {
    @SerializedName("id")
    long mId;
    @SerializedName("text")
    String mText;
    @SerializedName("created")
    long mCreated;
    boolean mPending;
    @SerializedName("client_id")
    String mClientId;
    @SerializedName("user_id")
    long mUserId;

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        this.mId = id;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        this.mText = text;
    }

    public long getCreated() {
        return mCreated;
    }

    public void setCreated(long created) {
        this.mCreated = created;
    }

    public boolean isPending() {
        return mPending;
    }

    public void setPending(boolean pending) {
        this.mPending = pending;
    }

    public String getClientId() {
        return mClientId;
    }

    public void setClientId(String clientId) {
        this.mClientId = clientId;
    }

    public long getUserId() {
        return mUserId;
    }

    public void setUserId(long userId) {
        this.mUserId = userId;
    }

    @SerializedName("created_at")
    public void setDate(String date) {
        mCreated = DateUtil.parseDate(date);
    }

    public String compositeUniqueKey() {
        return mUserId + "/" + mClientId;
    }

    public void validate() {
        if (mUserId < 1) {
            throw new ValidationFailedException("invalid user id");
        }
        if (mCreated < 1) {
            throw new ValidationFailedException("invalid created date");
        }
        if (StringUtils.isEmpty(mText)) {
            throw new ValidationFailedException("invalid text");
        }
        if (StringUtils.isEmpty(mClientId)) {
            throw new ValidationFailedException("invalid client id");
        }
    }
}
