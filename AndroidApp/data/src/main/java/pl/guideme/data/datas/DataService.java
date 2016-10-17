package pl.guideme.data.datas;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.LinkedList;
import java.util.List;

import pl.guideme.data.api.FeedResponse;
import pl.guideme.data.bus.EventBus;
import pl.guideme.data.cache.MemoryCache;
import pl.guideme.data.net.ServerEngine;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

@EBean(scope = EBean.Scope.Singleton)
public class DataService {

    private final Object mSync = new Object();
    private boolean mStarted;

    @Bean
    protected ServerEngine mServerEngine;
    @Bean
    protected MemoryCache mMemoryCache;
    @Bean
    protected AccountService mAccountService;
    @Bean
    protected EventBus mEventBus;

    public void start() {
        if (mStarted) {
            throw new IllegalStateException();
        }
        synchronized (mSync) {
            mStarted = true;
            loadCache();
        }
    }

    public List<FeedResponse> getFeeds() {
        List<FeedResponse> list = (List<FeedResponse>)mMemoryCache.getCachedObject();
        if (list == null){
            list = new LinkedList<>();
            mMemoryCache.setCachedObject(list);
        }
        return list;
    }

    private void loadCache() {
        if (!isCached()) {
            sync();
        }
    }

    private boolean isCached() {
        return mMemoryCache.getCachedObject() != null;
    }

    protected void sync() {
        String token = mAccountService.getUser().getToken();

        mServerEngine.getFeedsApi().feed(token, new Callback<FeedResponse>() {
            @Override
            public void success(FeedResponse feedResponse, Response response) {
                cacheLoadedData(feedResponse);
            }

            @Override
            public void failure(RetrofitError error) {
                reportFailure(error);
            }
        });
    }

    private void cacheLoadedData(FeedResponse feedResponse) {
        synchronized (mSync) {
            mMemoryCache.setCachedObject(feedResponse);
        }
        mEventBus.publish(new FeedLoadedEventAction(feedResponse));
    }

    private void reportFailure(RetrofitError error) {
        mEventBus.publish(new DataLoadingFailureAction(FeedResponse.class, error));
    }
}
