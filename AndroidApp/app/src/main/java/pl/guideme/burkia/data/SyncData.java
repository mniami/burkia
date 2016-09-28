package pl.guideme.burkia.data;

import java.util.LinkedList;
import java.util.List;
import java.util.TimerTask;

public class SyncData<T> {
    private final Object synch = new Object();
    private GenericData<T> mLocalData;
    private GenericData<T> mRemoteData;
    private List<SyncListener<T>> mSyncListeners;

    public SyncData() {
        mLocalData = new GenericData<>();
        mRemoteData = new GenericData<>();
        mSyncListeners = new LinkedList<>();
    }

    private static <T> T getData(GenericData<T> data, ReturnData<T> defaultValue) {
        if (!data.isSet()) {
            if (defaultValue != null) {
                return defaultValue.get();
            } else {
                return null;
            }
        } else {
            if (data.isLocked()) {
                data.waitForUnlock();
            }
            return data.get();
        }
    }

    private static <T> void loadDataAsync(final GenericData<T> data, final SyncAction<T> syncLocalAction) {
        loadDataAsync(data, syncLocalAction, null);
    }

    private static <T> void loadDataAsync(final GenericData<T> data, final SyncAction<T> syncLocalAction, final Runnable onFinished) {
        new Thread(new TimerTask() {
            @Override
            public void run() {
                try {
                    loadData(data, syncLocalAction);
                } finally {
                    if (onFinished != null) {
                        onFinished.run();
                    }
                }
            }
        }).start();
    }

    private static <T> void loadData(GenericData<T> data, SyncAction<T> syncLocalAction) {
        data.reset();
        data.lock();
        try {
            data.set(syncLocalAction.loadRemoteData());
        } finally {
            data.unlock();
        }
    }

    public boolean isLocalDataLoaded() {
        return mLocalData.isSet();
    }

    public boolean isRemoteDataLoaded() {
        return mRemoteData.isSet();
    }

    public T getData() {
        return getData(null, false);
    }

    public T getData(ReturnData<T> defaultValue, boolean waitForRemoteData) {
        if (waitForRemoteData && mRemoteData.isSet() && !mRemoteData.isLocked()) {
            return getData(mRemoteData, defaultValue);
        } else {
            return getData(mLocalData, defaultValue);
        }
    }

    public void sync(SyncAction<T> syncLocalAction, SyncAction<T> syncRemoteAction) {
        synchronized (synch) {
            loadDataAsync(mLocalData, syncLocalAction);
            loadDataAsync(mRemoteData, syncRemoteAction, new Runnable() {
                @Override
                public void run() {
                    onRemoteDataLoaded();
                }
            });
        }
    }

    private void onRemoteDataLoaded() {
        if (mLocalData.isLocked()) {
            mLocalData.waitForUnlock();
        }

    }

    private void saveLoadedData() {

    }

    public interface SyncListener<T> {
        void onSync(SyncData<T> syncData);
    }

    public interface SyncAction<T> {
        T loadRemoteData();
    }

    public interface ReturnData<T> {
        T get();
    }
}