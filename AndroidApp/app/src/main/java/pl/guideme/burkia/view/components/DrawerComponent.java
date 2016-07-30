package pl.guideme.burkia.view.components;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import org.androidannotations.annotations.EBean;

import pl.guideme.burkia.view.components.base.ComponentAdapter;
import pl.guideme.burkia.view.components.base.ComponentContainer;
import pl.guideme.burkia.view.fragments.DrawerFragment;

@EBean
public class DrawerComponent extends ComponentAdapter {
    private ToolbarComponent mToolbarComponent;
    private FragmentComponent mFragmentComponent;

    @Override
    public void onCreate(FragmentActivity activity, Context context, View parentView, ComponentContainer componentContainer) {
        super.onCreate(activity, context, parentView, componentContainer);
        mToolbarComponent = componentContainer.get(ToolbarComponent.class);
        mFragmentComponent = componentContainer.get(FragmentComponent.class);
    }

    @Override
    protected void onFragmentResumed(Fragment fragment) {
        super.onFragmentResumed(fragment);

        mToolbarComponent.setHamburgerListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onHamburgerClick();
            }
        });
    }

    @Override
    protected void onFragmentPaused(Fragment fragment) {
        mToolbarComponent.clearHamburgerListener();
    }

    public void show() {
        final DrawerFragment drawerFragment = new DrawerFragment();
        drawerFragment.setFragmentListener(getFragmentListener());
        mFragmentComponent.change(drawerFragment, false);
    }
    @Override
    protected void onFragmentAction(Bundle actionArgument) {
        mToolbarComponent.animateHamburgerCross();
        mFragmentComponent.popBackStack();
    }

    private void onHamburgerClick() {
    }
}
