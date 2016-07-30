package pl.guideme.burkia.view.components;

import android.os.Bundle;

import org.androidannotations.annotations.EBean;

import pl.guideme.burkia.R;
import pl.guideme.burkia.view.components.base.ActionKeys;
import pl.guideme.burkia.view.components.base.ComponentAdapter;
import pl.guideme.burkia.view.fragments.FeedListFragment;

@EBean
public class FeedListComponent extends ComponentAdapter {
    public void show(){
        if (activity == null){
            throw new IllegalStateException();
        }
        ToolbarComponent toolbarComponent = mContainer.get(ToolbarComponent.class);
        toolbarComponent.animateHamburgerCross();

        FeedListFragment fragment = new FeedListFragment();
        fragment.setFragmentListener(getFragmentListener());
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onFragmentAction(Bundle actionArguments) {
        final DrawerComponent drawerComponent = mContainer.get(DrawerComponent.class);

        int actionId = actionArguments.getInt(ActionKeys.ACTION_NAME);
        if (actionId == FeedListFragment.FEED_LIST_ITEM_CLICKED){
            drawerComponent.show();
        }
    }
}
