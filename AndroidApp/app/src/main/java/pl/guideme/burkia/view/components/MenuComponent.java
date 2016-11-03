package pl.guideme.burkia.view.components;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import pl.guideme.burkia.view.fragments.FeedListFragment;
import pl.guideme.componentslib.ActionKeys;
import pl.guideme.componentslib.FragmentComponentAdapter;

public class MenuComponent extends FragmentComponentAdapter {
    @Override
    public void onFragmentAction(Fragment fragment, Bundle actionArguments) {
        final DrawerComponent drawerComponent = mContainer.get(DrawerComponent.class);

        int actionId = actionArguments.getInt(ActionKeys.ACTION_NAME);
        if (actionId == FeedListFragment.FEED_LIST_ITEM_CLICKED){
            drawerComponent.show();
        }
    }
}
