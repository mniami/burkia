package pl.guideme.burkia.view.components;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.androidannotations.annotations.EBean;

import pl.guideme.burkia.view.fragments.FeedListFragment_;
import pl.guideme.componentslib.ActionKeys;
import pl.guideme.componentslib.FragmentComponentAdapter;
import pl.guideme.burkia.view.fragments.FeedListFragment;
import pl.guideme.data.logs.Log;

@EBean
public class FeedListComponent extends FragmentComponentAdapter {
    private static final Log log = Log.withName("FeedListComponent");

    public void show(){
        log.info(()->"Show called");
        if (mActivity == null){
            throw new IllegalStateException();
        }
        ToolbarComponent toolbarComponent = mContainer.get(ToolbarComponent.class);
        toolbarComponent.animateHamburgerCross();
        FragmentComponent fragmentComponent = mContainer.get(FragmentComponent.class);

        FeedListFragment fragment = FeedListFragment_.builder().build();
        fragment.attachToComponent(this);
        fragmentComponent.change(fragment, true);
    }

    @Override
    public void onFragmentAction(Fragment fragment, Bundle actionArguments) {
        final DrawerComponent drawerComponent = mContainer.get(DrawerComponent.class);

        int actionId = actionArguments.getInt(ActionKeys.ACTION_NAME);
        if (actionId == FeedListFragment.FEED_LIST_ITEM_CLICKED){
            drawerComponent.show();
        }
    }
}
