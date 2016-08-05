package pl.guideme.burkia.view.components;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import org.androidannotations.annotations.EBean;
import org.apache.commons.lang3.StringUtils;

import pl.guideme.burkia.R;
import pl.guideme.burkia.util.L;
import pl.guideme.componentslib.FragmentComponentAdapter;

@EBean
public class FragmentComponent extends FragmentComponentAdapter {
    public void popBackStack() {
        L.d("Pop back stack called");
        if (mActivity == null) {
            return;
        }
        mActivity.getSupportFragmentManager().popBackStack();
    }
    public void change(Fragment fragment) {
        change(fragment, true);
    }

    public void change(Fragment fragment, boolean addToBackStack) {
        if (!isVisible()) {
            return;
        }
        String backStackTag;
        if (addToBackStack) {
            backStackTag = fragment.getClass().getName();
        } else {
            backStackTag = getLastBackStackTag();
        }

        L.d("Change fragment to: " + fragment.getClass().getName() + " with backStack tag: " + (backStackTag != null ? backStackTag : StringUtils.EMPTY));

        FragmentTransaction transaction = mActivity.getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.animation_exit, R.anim.animation_enter)
                .replace(R.id.fragment_container, fragment);
        if (backStackTag != null) {
            transaction.addToBackStack(backStackTag);
        }
        transaction.commit();
    }

    private String getLastBackStackTag() {
        int count = mActivity.getSupportFragmentManager().getBackStackEntryCount();
        if (count > 0) {
            return mActivity.getSupportFragmentManager().getBackStackEntryAt(count - 1).getName();
        }
        return null;
    }
}
