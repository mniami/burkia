package pl.guideme.burkia.view.components.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

public class ComponentAdapter implements Component {
    protected ComponentContainer mContainer;
    protected FragmentActivity activity;
    protected View mParentView;
    protected FragmentListener fragmentListener;

    @Override
    public void onCreate(FragmentActivity activity, Context context, View view, ComponentContainer container) {
        this.activity = activity;
        this.mContainer = container;
        fragmentListener = new FragmentListener() {
            @Override
            public void fragmentStarted(Fragment fragment) {
                onFragmentStarted(fragment);
            }

            @Override
            public void fragmentResumed(Fragment fragment) {
                onFragmentResumed(fragment);
            }

            @Override
            public void fragmentPaused(Fragment fragment) {
                onFragmentPaused(fragment);
            }

            @Override
            public void fragmentStopped(Fragment fragment) {
                onFragmentStopped(fragment);
            }

            @Override
            public void fragmentDestroyed(Fragment fragment) {
                onFragmentDestroyed(fragment);
            }

            @Override
            public void fragmentAction(Bundle actionArgument) {
                ComponentAdapter.this.onFragmentAction(actionArgument);
            }
        };
        mParentView = view;
    }

    protected FragmentListener getFragmentListener(){
        return fragmentListener;
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

    protected void onFragmentStarted(Fragment fragment) {

    }

    protected void onFragmentResumed(Fragment fragment) {

    }

    protected void onFragmentPaused(Fragment fragment) {

    }

    protected void onFragmentStopped(Fragment fragment) {

    }

    protected void onFragmentDestroyed(Fragment fragment) {

    }

    protected void onFragmentAction(Bundle actionArgument) {

    }
}
