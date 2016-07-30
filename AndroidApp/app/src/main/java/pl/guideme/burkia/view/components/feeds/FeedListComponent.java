package pl.guideme.burkia.view.components.feeds;

import org.androidannotations.annotations.EBean;

import pl.guideme.burkia.R;
import pl.guideme.burkia.view.components.base.ComponentAdapter;
import pl.guideme.burkia.view.components.toolbar.ToolbarComponent;
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
        fragment.setFragmentListener(this);
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
