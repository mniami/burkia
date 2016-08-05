package pl.guideme.burkia.view.components;

import android.os.Bundle;

import org.androidannotations.annotations.EBean;

import pl.guideme.componentslib.ActionKeys;
import pl.guideme.componentslib.FragmentComponentAdapter;
import pl.guideme.burkia.view.fragments.FeedListFragment;

@EBean
public class FeedListComponent extends FragmentComponentAdapter {

    public void show(){
        if (mActivity == null){
            throw new IllegalStateException();
        }
        ToolbarComponent toolbarComponent = mContainer.get(ToolbarComponent.class);
        toolbarComponent.animateHamburgerCross();
        FragmentComponent fragmentComponent = mContainer.get(FragmentComponent.class);

        FeedListFragment fragment = new FeedListFragment();
        fragment.attachToComponent(getComponentId());
        fragmentComponent.change(fragment, true);
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
