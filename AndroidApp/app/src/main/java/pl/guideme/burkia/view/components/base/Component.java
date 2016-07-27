package pl.guideme.burkia.view.components.base;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;

public interface Component {
    void onCreate(FragmentActivity activity, Context context, View view);
    void onResume();
    void onStop();
    void onPause();
    void onDestroy();
}
