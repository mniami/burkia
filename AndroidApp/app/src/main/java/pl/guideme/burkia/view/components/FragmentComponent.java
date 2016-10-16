package pl.guideme.burkia.view.components;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import org.androidannotations.annotations.EBean;
import org.apache.commons.lang3.StringUtils;

import pl.guideme.burkia.R;
import pl.guideme.componentslib.Activity;
import pl.guideme.componentslib.ActivityAction;
import pl.guideme.componentslib.FragmentComponentAdapter;
import pl.guideme.data.logs.Log;

@EBean
public class FragmentComponent extends FragmentComponentAdapter {
    private static final Log log = Log.withName("FragmentComponent");
    private Fragment fragmentToChanged;
    private boolean fragmentToChangeAddToBackStack;

    public void popBackStack() {
        log.fine(()->"Pop back stack called");
        if (mActivity == null) {
            return;
        }
        mActivity.getSupportFragmentManager().popBackStack();
    }
    public void change(Fragment fragment) {
        change(fragment, true);
    }

    public void change(Fragment fragment, boolean addToBackStack) {
        log.fine(()->"change called");
        if (!isActivityCreated()) {
            fragmentToChanged = fragment;
            fragmentToChangeAddToBackStack = addToBackStack;
            log.fine(()->"Activity is not visible, change fragment canceled.");
            return;
        }
        String backStackTag;
        if (addToBackStack) {
            backStackTag = fragment.getClass().getName();
        } else {
            backStackTag = getLastBackStackTag();
        }

        log.fine(()->"Change fragment to: " + fragment.getClass().getName() + " withName backStack tag: " + (backStackTag != null ? backStackTag : StringUtils.EMPTY));

        FragmentTransaction transaction = mActivity.getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.animation_enter, R.anim.animation_exit)
                .replace(R.id.fragment_container, fragment);
        if (backStackTag != null) {
            transaction.addToBackStack(backStackTag);
        }
        transaction.commit();
    }

    @Override
    public void onActivityAction(Activity activity, ActivityAction activityAction){
        if (activityAction == ActivityAction.Resumed) {
            if (fragmentToChanged != null) {
                final Fragment fragment = fragmentToChanged;
                final boolean addToBackStack = fragmentToChangeAddToBackStack;

                fragmentToChanged = null;
                fragmentToChangeAddToBackStack = false;

                change(fragment, addToBackStack);
            }
        }
        super.onActivityAction(activity, activityAction);
    }

    private String getLastBackStackTag() {
        if (mActivity != null) {
            int count = mActivity.getSupportFragmentManager().getBackStackEntryCount();
            if (count > 0) {
                return mActivity.getSupportFragmentManager().getBackStackEntryAt(count - 1).getName();
            }
        }
        return null;
    }
}
