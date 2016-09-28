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

package pl.guideme.burkia.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.StringUtils;

import pl.guideme.burkia.util.Validation;
import pl.guideme.burkia.util.ValidationFailedException;

public class User implements Validation {
    @JsonProperty("id")
    long mId;

    @JsonProperty("name")
    String mName;

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    @Override
    public void validate() {
        if (mId < 1) {
            throw new ValidationFailedException("invalid user id");
        }
        if (StringUtils.isEmpty(mName)) {
            throw new ValidationFailedException("invalid mName");
        }
    }
}
