package pl.guideme.data;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.WakefulBroadcastReceiver;

import org.androidannotations.annotations.EBean;

import java.util.LinkedList;
import java.util.List;

import pl.guideme.data.logs.Log;

/**
 * Created by szymon on 10.12.15.
 */

@EBean(scope = EBean.Scope.Singleton)
public class ConnectivityReceiver extends WakefulBroadcastReceiver {

    private static Log sLogger = Log.withName("Connectivity Receiver");

    private ConnectivityManager mConnectivityManager;

    private List<Runnable> mListeners = new LinkedList<>();

    public void addListener(Runnable listener){
        mListeners.add(listener);
    }

    public void removeListener(Runnable listener){
        mListeners.remove(listener);
    }

    public void init(ConnectivityManager connectivityManager){
        mConnectivityManager = connectivityManager;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public boolean isInternetConnection() {
        NetworkInfo activeNetworkInfo;
        if (mConnectivityManager != null) {
            activeNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        } else {
            activeNetworkInfo = null;
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
