package pl.guideme.burkia.view.components;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import org.androidannotations.annotations.EBean;
import org.apache.commons.lang3.StringUtils;

import pl.guideme.burkia.R;
import pl.guideme.burkia.util.L;
import pl.guideme.burkia.view.components.base.ComponentAdapter;

@EBean
public class FragmentComponent extends ComponentAdapter {
    public void popBackStack() {
        L.d("Pop back stack called");
        if (activity == null) {
            return;
        }
        activity.getSupportFragmentManager().popBackStack();
    }

    public void change(Fragment fragment) {
        change(fragment, true);
    }

    public void change(Fragment fragment, boolean addToBackStack) {
        if (activity == null) {
            return;
        }
        String backStackTag;
        if (addToBackStack) {
            backStackTag = fragment.getClass().getName();
        } else {
            backStackTag = getLastBackStackTag();
        }

        L.d("Change fragment to: " + fragment.getClass().getName() + " with backStack tag: " + (backStackTag != null ? backStackTag : StringUtils.EMPTY));

        FragmentTransaction transaction = activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment);
        if (backStackTag != null) {
            transaction.addToBackStack(backStackTag);
        }
        transaction.commit();
    }

    private String getLastBackStackTag() {
        int count = activity.getSupportFragmentManager().getBackStackEntryCount();
        if (count > 0) {
            return activity.getSupportFragmentManager().getBackStackEntryAt(count - 1).getName();
        }
        return null;
    }
}
