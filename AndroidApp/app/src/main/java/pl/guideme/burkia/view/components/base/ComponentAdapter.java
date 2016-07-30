package pl.guideme.burkia.view.components.base;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import pl.guideme.burkia.view.fragments.FragmentListener;

public class ComponentAdapter implements Component, FragmentListener {
    protected ComponentContainer mContainer;
    protected FragmentActivity activity;
    protected View mParentView;

    @Override
    public void onCreate(FragmentActivity activity, Context context, View view, ComponentContainer container) {
        this.activity = activity;
        this.mContainer = container;
        mParentView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {
        mContainer = null;
        activity = null;
        mParentView = null;
    }

    @Override
    public void fragmentStarted(Fragment fragment) {

    }

    @Override
    public void fragmentResumed(Fragment fragment) {

    }

    @Override
    public void fragmentPaused(Fragment fragment) {

    }

    @Override
    public void fragmentStopped(Fragment fragment) {

    }

    @Override
    public void fragmentDestroyed(Fragment fragment) {

    }
}
