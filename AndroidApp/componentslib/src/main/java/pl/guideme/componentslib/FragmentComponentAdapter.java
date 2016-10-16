package pl.guideme.componentslib;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

/**
 * Base component adapter that gives the opportunity to work withName Fragments withName Android Component Oriented programming
 */
@EBean
public class FragmentComponentAdapter implements Component {
    private static int componentIdIncrementer = 0;

    @Bean
    protected ComponentContainer mContainer;
    protected int componentId;
    protected BaseFragment mFragment;
    protected Activity mActivity;

    static int getNewComponentId() {
        return ++componentIdIncrementer;
    }

    @Override
    public void initialize(Context context, ComponentContainer container) {
        this.mContainer = container;

        if (componentId == 0) {
            componentId = getNewComponentId();
        }
    }

    public Context getContext(){
        if (mActivity != null){
            return mActivity.getApplicationContext();
        }
        return null;
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
    public void onActivityAction(Activity activity, ActivityAction activityAction) {
        if (activityAction == ActivityAction.Created) {
            mActivity = activity;
        } else if (activityAction == ActivityAction.Destroyed){
            mActivity = null;
        }
    }

    @Override
    public void onFragmentAction(Fragment fragment, FragmentAction fragmentAction) {
        // Do nothing
    }

    @Override
    public void onFragmentAction(Fragment fragment, Bundle actionArguments) {
        // Do nothing
    }

    protected boolean isActivityVisible() {
        return mActivity != null && mActivity.isVisible();
    }

    protected boolean isActivityCreated(){
        return mActivity != null && mActivity.isCreated();
    }
}
