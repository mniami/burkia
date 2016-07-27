package pl.guideme.burkia.view.components.dispatchers;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import org.androidannotations.annotations.EBean;

import pl.guideme.burkia.view.components.base.ComponentContainer;
import pl.guideme.burkia.view.components.base.DispatcherAdapter;
import pl.guideme.burkia.view.components.toolbar.MainToolbar;
import pl.guideme.burkia.view.fragments.Drawer;
import pl.guideme.burkia.view.components.ChangeFragment;

@EBean
public class DrawerDispatcher extends DispatcherAdapter {
    private MainToolbar mMainToolbarPresenter;
    private ChangeFragment mChangeFragment;

    @Override
    public void onCreate(FragmentActivity activity, Context context, ComponentContainer componentContainer) {
        mMainToolbarPresenter = componentContainer.get(MainToolbar.class);
        mChangeFragment = componentContainer.get(ChangeFragment.class);

        mMainToolbarPresenter.setHamburgerListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onHamburgerClick();
            }
        });
    }

    @Override
    public void onPause() {
        mMainToolbarPresenter.clearHamburgerListener();
    }

    private void onHamburgerClick() {
        Fragment currentFragment = mChangeFragment.getCurrentFragment();
        if (currentFragment instanceof Drawer) {
            boolean handled = ((Drawer) currentFragment).closeClicked();
            if (!handled) {
                mChangeFragment.popBackStack();
            }
        } else {
            mMainToolbarPresenter.animateHamburgerCross();
            mChangeFragment.change(new Drawer());
        }
    }
}
