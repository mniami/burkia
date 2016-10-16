package pl.guideme.data.logs;

import retrofit.RestAdapter;

public class AndroidLogger implements RestAdapter.Log {

    private String mTag;

    public AndroidLogger(String tag) {
        mTag = tag;
    }

    @Override
    public void log(String message) {
        android.util.Log.d(mTag, message);
    }
}
