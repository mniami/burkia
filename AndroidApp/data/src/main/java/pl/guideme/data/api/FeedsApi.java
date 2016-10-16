package pl.guideme.data.api;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

public interface FeedsApi {

    @GET("/feed")
    void feed(@Header("Authorization") String authorizationToken, Callback<FeedResponse> callback);

    @GET("/user_feed/{userId}")
    void userFeed(@Path("userId") long userId, @Query("since") long since, Callback<FeedResponse> callback);

    @POST("new_post")
    void sendPost(@Query("text") String text, @Query("client_id") String clientId,
                                   // ultra secure API sending user id :p
                                   @Query("user_id") long userId, Callback<NewPostResponse> callback);
}
