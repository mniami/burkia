package pl.guideme.burkia.config;

public interface AppConfig {
    long getUserId();

    void setUserId(long userId);

    int getNewPostRetryCount();

    void setNewPostRetryCount(int count);

    String getApiUrl();

    void setApiUrl(String url);
}
