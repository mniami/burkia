package pl.guideme.burkia.api;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

public interface ApiServiceEndpoint {

    @GET("/feed.json")
    Call<FeedResponse> feed(@Query("since") long since);

    @GET("/user_feed/{userId}.json")
    Call<FeedResponse> userFeed(@Path("userId") long userId, @Query("since") long since);

    @POST("new_post.json")
    Call<NewPostResponse> sendPost(@Query("text") String text, @Query("client_id") String clientId,
                                   // ultra secure API sending user id :p
                                   @Query("user_id") long userId);
}
