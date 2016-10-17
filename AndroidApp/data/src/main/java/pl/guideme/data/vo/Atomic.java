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

public class Atomic {
    @SerializedName("values")
    private KeyValuePair[] mValues;
    @SerializedName("tags")
    private String[] mTags;
    @SerializedName("imageUrl")
    private String mImageUrl;
    @SerializedName("name")
    private String mName;

    public KeyValuePair[] getValues() {
        return mValues;
    }

    public String[] getTags() {
        return mTags;
    }

    public void setValues(KeyValuePair[] mValues) {
        this.mValues = mValues;
    }

    public void setTags(String[] mTags) {
        this.mTags = mTags;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
