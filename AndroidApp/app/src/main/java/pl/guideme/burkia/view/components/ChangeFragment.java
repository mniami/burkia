package pl.guideme.burkia.view.components;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import org.androidannotations.annotations.EBean;

import pl.guideme.burkia.R;
import pl.guideme.burkia.view.components.base.ComponentAdapter;

@EBean
public class ChangeFragment extends ComponentAdapter {

    private FragmentActivity activity;

    @Override
    public void onCreate(FragmentActivity activity, Context context, View view) {
        this.activity = activity;
    }

    @Override
    public void onStop() {
        this.activity = null;
    }

    public <T> boolean isCurrentFragment(Class<T> itemClass) {
        return getCurrentFragmentName().equals(itemClass.getName());
    }

    private String getCurrentFragmentName() {
        return null;
    }

    public void popBackStack() {

    }

    public void change(Fragment fragment) {
        if (activity == null) {
            return;
        }
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(fragment.getClass().getName())
                .commit();
    }

    public Fragment getCurrentFragment() {
        return activity.getSupportFragmentManager().findFragmentById(R.id.fragment_container);
    }
}