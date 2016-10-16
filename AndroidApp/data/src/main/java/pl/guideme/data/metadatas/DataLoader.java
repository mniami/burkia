package pl.guideme.data.metadatas;

import java.util.LinkedList;
import java.util.List;

import pl.guideme.data.logs.Log;
import pl.guideme.data.datas.DataCache;
import pl.guideme.data.util.Consumer;
import pl.guideme.data.vo.Metadata;

public class DataLoader<T> {
    public enum Mode {
        Synced,
        Offline,
        Syncing
    }
    private final static Log logger = Log.withName("DataLoader");
    private final Class<T> mTypeParameterClass;
    private String mCacheKey;
    private ApiCall mApiCall;
    private T mItem;
    private Metadata mMetadata;

    protected DataCache mCache;

    public DataLoader(Class<T> typeParameterClass, String cacheKey, ApiCall apiCall, DataCache dataCache) {
        this.mTypeParameterClass = typeParameterClass;
        mCacheKey = cacheKey;
        mApiCall = apiCall;
        mCache = dataCache;
    }

    public T getItem() {
        return mItem;
    }

    public void start() {
        logger.fine(() -> "start called");

        if (loadCache()) {

        } else {
            callMetadata();
        }
    }

    public Metadata getMetadata() {
        return mMetadata;
    }

    public void setMetadata(Metadata metadata) {
        analyzeMetadata(metadata);
    }

    private void analyzeMetadata(Metadata newMetadata) {
        Metadata oldMetadata = mMetadata;

        if (oldMetadata == null){
            logger.fine(()->"Nothing to compare, old metadata doesn't exist.");
            return;
        }
        String oldRevision = oldMetadata.getValue(Consts.REVISION_KEY);
        String newRevision = newMetadata.getValue(Consts.REVISION_KEY);

        if (RevisionHelper.isServerRevisionHigher(oldRevision, newRevision)){
            callSync();
        }
    }

    private void callSync() {

    }

    private void callMetadata() {
        logger.fine(() -> "callMetadata called");

        if (mApiCall.getMetadataCallback() != null){
            mApiCall.getMetadataCallback().run();
        }
    }

    private boolean loadCache() {
        logger.fine(() -> "loadCache called");
        mItem = mCache.get(mCacheKey, mTypeParameterClass);
        return mItem != null;
    }

    public class SyncArgs {
        private List<Consumer<T>> mConsumers = new LinkedList<>();
        private Mode mMode;

        public void onSyncConsumer(Consumer<T> consumer) {
            mConsumers.add(consumer);
            onLoad();
        }

        protected void onLoad() {
            for (Consumer<T> consumer : mConsumers) {
                consumer.run(mItem);
            }
        }

        public void setItem(String cacheKey, T lItem, Mode mode) {
            mItem = lItem;
            mMode = mode;

            mCache.put(cacheKey, lItem);

            onLoad();
        }
    }

    public static class ApiCall {
        private Runnable metadataCallback;
        private Runnable syncCallback;

        public Runnable getMetadataCallback() {
            return metadataCallback;
        }

        public void setMetadataCallback(Runnable metadataCallback) {
            this.metadataCallback = metadataCallback;
        }

        public Runnable getSyncCallback() {
            return syncCallback;
        }

        public void setSyncCallback(Runnable syncCallback) {
            this.syncCallback = syncCallback;
        }
    }

}
