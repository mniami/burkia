package pl.guideme.burkia.view.components;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.androidannotations.annotations.EBean;

import pl.guideme.burkia.view.fragments.FeedListFragment;
import pl.guideme.burkia.view.fragments.FeedListFragment_;
import pl.guideme.burkia.view.fragments.FluffyAvatarFragment;
import pl.guideme.burkia.view.fragments.FluffyAvatarFragment_;
import pl.guideme.componentslib.ActionKeys;
import pl.guideme.componentslib.FragmentComponentAdapter;
import pl.guideme.data.logs.Log;

@EBean
public class FluffyAvatarComponent extends FragmentComponentAdapter {
    private static final Log log = Log.withName("FluffyAvatarComponent");

    @Override
    public void onFragmentAction(Fragment fragment, Bundle actionArguments) {
//        final DrawerComponent drawerComponent = mContainer.get(DrawerComponent.class);
//
//        int actionId = actionArguments.getInt(ActionKeys.ACTION_NAME);
//        if (actionId == FeedListFragment.FEED_LIST_ITEM_CLICKED) {
//            drawerComponent.show();
//        }
    }

    public void show() {
        log.info(() -> "Show called");
        if (mActivity == null) {
            throw new IllegalStateException();
        }
        FragmentComponent fragmentComponent = mContainer.get(FragmentComponent.class);

        FluffyAvatarFragment fragment = FluffyAvatarFragment_.builder().build();
        fragment.attachToComponent(this);
        fragmentComponent.change(fragment, true);
    }
}
