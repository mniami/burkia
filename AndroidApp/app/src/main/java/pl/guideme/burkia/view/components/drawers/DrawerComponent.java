package pl.guideme.burkia.view.components.drawers;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import org.androidannotations.annotations.EBean;

import pl.guideme.burkia.view.components.ChangeFragment;
import pl.guideme.burkia.view.components.base.ComponentAdapter;
import pl.guideme.burkia.view.components.base.ComponentContainer;
import pl.guideme.burkia.view.components.toolbar.ToolbarComponent;
import pl.guideme.burkia.view.fragments.Drawer;

@EBean
public class DrawerComponent extends ComponentAdapter {
    private ToolbarComponent mToolbarComponent;
    private ChangeFragment mChangeFragment;

    @Override
    public void onCreate(FragmentActivity activity, Context context, View parentView, ComponentContainer componentContainer) {
        super.onCreate(activity, context, parentView, componentContainer);
        mToolbarComponent = componentContainer.get(ToolbarComponent.class);
        mChangeFragment = componentContainer.get(ChangeFragment.class);
    }

    @Override
    public void onResume(){
        super.onResume();
        mToolbarComponent.setHamburgerListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onHamburgerClick();
            }
        });
    }

    @Override
    public void onPause() {
        mToolbarComponent.clearHamburgerListener();
    }

    private void onHamburgerClick() {
        Fragment currentFragment = mChangeFragment.getCurrentFragment();
        if (currentFragment instanceof Drawer) {
            boolean handled = ((Drawer) currentFragment).closeClicked();
            if (!handled) {
                mChangeFragment.popBackStack();
            }
        } else {
            mToolbarComponent.animateHamburgerCross();
            mChangeFragment.change(new Drawer());
        }
    }
}
