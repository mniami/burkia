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

package pl.guideme.burkia.api;

import org.androidannotations.annotations.EBean;
import org.apache.commons.lang3.StringUtils;

import pl.guideme.burkia.configuration.AppConfig;
import retrofit.Call;
import retrofit.Retrofit;

@EBean(scope = EBean.Scope.Singleton)
public class ApiService implements ApiServiceEndpoint {
    public static final String API_SERVICE_CONFIG_NAME = "ApiService";
    protected static final String EMPTY_URL_ERROR = "Empty apiService URL";

    protected ApiServiceEndpoint apiServiceEndpoint;
    private boolean initialized;

    public void initialize(AppConfig configuration) {
        if (initialized) {
            throw new IllegalStateException();
        }
        String apiServiceUrl = configuration.get(API_SERVICE_CONFIG_NAME);

        if (StringUtils.isEmpty(apiServiceUrl)){
            throw new IllegalStateException(EMPTY_URL_ERROR);
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(apiServiceUrl)
                .build();

        apiServiceEndpoint = retrofit.create(ApiServiceEndpoint.class);
        initialized = true;
    }

    @Override
    public Call<FeedResponse> feed(long since) {
        if (!initialized) {
            throw new IllegalStateException();
        }
        return apiServiceEndpoint.feed(since);
    }

    @Override
    public Call<FeedResponse> userFeed(long userId, long since) {
        if (!initialized) {
            throw new IllegalStateException();
        }
        return null;
    }

    @Override
    public Call<NewPostResponse> sendPost(String text, String clientId, long userId) {
        if (!initialized) {
            throw new IllegalStateException();
        }
        return null;
    }

    protected boolean isInitialized(){
        return initialized;
    }
}
