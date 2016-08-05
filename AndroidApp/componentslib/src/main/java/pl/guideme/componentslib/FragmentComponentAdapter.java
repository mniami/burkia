package pl.guideme.componentslib;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

/**
 * Base component adapter that gives the opportunity to work with Fragments with Android Component Oriented programming
 */
public class FragmentComponentAdapter implements Component {
    private static int componentIdIncrementer = 0;

    protected ComponentContainer mContainer;
    protected FragmentListener mFragmentListener;
    protected int componentId;
    protected BaseFragment mFragment;
    protected AppCompatActivity mActivity;

    static int getNewComponentId() {
        return ++componentIdIncrementer;
    }

    @Override
    public void onCreate(Context context, ComponentContainer container) {
        this.mContainer = container;

        if (componentId == 0) {
            componentId = getNewComponentId();
        }

        mFragmentListener = new FragmentListenerHandler();
    }

    @Override
    public void onDestroy() {
        mContainer = null;
    }

    @Override
    public int getComponentId() {
        return componentId;
    }

    @Override
    public void register(Fragment fragment) {
        if (fragment instanceof BaseFragment) {
            BaseFragment baseFragment = (BaseFragment) fragment;
            baseFragment.setFragmentListener(mFragmentListener);
            this.mFragment = baseFragment;
            this.mActivity = (AppCompatActivity) baseFragment.getActivity();
        }
    }

    @Override
    public void onActivityCreated(AppCompatActivity activity) {
        // Do nothing
    }

    @Override
    public void onActivityDestroy(AppCompatActivity activity) {
        // Do nothing
    }

    protected boolean isVisible() {
        return mFragment != null && mFragment.isVisible();
    }

    protected void onFragmentStarted(Fragment fragment) {
        // Do nothing
    }

    protected void onFragmentResumed(Fragment fragment) {
        // Do nothing
    }

    protected void onFragmentPaused(Fragment fragment) {
        // Do nothing
    }

    protected void onFragmentStopped(Fragment fragment) {
        // Do nothing
    }

    protected void onFragmentDestroyed(Fragment fragment) {
        // Do nothing
    }

    protected void onFragmentAction(Bundle actionArgument) {
        // Do nothing
    }

    class FragmentListenerHandler implements FragmentListener {
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
            mActivity = null;
            mFragment = null;
        }

        @Override
        public void fragmentAction(Bundle actionArgument) {
            FragmentComponentAdapter.this.onFragmentAction(actionArgument);
        }
    }
}
