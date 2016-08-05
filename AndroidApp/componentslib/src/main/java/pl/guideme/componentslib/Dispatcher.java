package pl.guideme.componentslib;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

public interface Dispatcher {
    void onCreate(FragmentActivity activity, Context context, ComponentContainer container);

    void onResume();

    void onStop();

    void onPause();

    void onDestroy();
}
