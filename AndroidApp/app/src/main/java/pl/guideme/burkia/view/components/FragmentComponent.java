package pl.guideme.burkia.view.components;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.EBean;
import org.apache.commons.lang3.StringUtils;

import pl.guideme.burkia.R;
import pl.guideme.componentslib.Activity;
import pl.guideme.componentslib.ActivityAction;
import pl.guideme.componentslib.util.L;
import pl.guideme.componentslib.FragmentComponentAdapter;

@EBean
public class FragmentComponent extends FragmentComponentAdapter {
    private static final L log = L.getL("FragmentComponent");
    private Fragment fragmentToChanged;
    private boolean fragmentToChangeAddToBackStack;

    public void popBackStack() {
        log.i("Pop back stack called");
        if (mActivity == null) {
            return;
        }
        mActivity.getSupportFragmentManager().popBackStack();
    }
    public void change(Fragment fragment) {
        change(fragment, true);
    }

    public void change(Fragment fragment, boolean addToBackStack) {
        log.i("change called");
        if (!isActivityCreated()) {
            fragmentToChanged = fragment;
            fragmentToChangeAddToBackStack = addToBackStack;
            log.i("Activity is not visible, change fragment canceled.");
            return;
        }
        String backStackTag;
        if (addToBackStack) {
            backStackTag = fragment.getClass().getName();
        } else {
            backStackTag = getLastBackStackTag();
        }

        log.i("Change fragment to: " + fragment.getClass().getName() + " with backStack tag: " + (backStackTag != null ? backStackTag : StringUtils.EMPTY));

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
