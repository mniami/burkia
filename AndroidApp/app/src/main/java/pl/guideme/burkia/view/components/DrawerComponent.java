package pl.guideme.burkia.view.components;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import org.androidannotations.annotations.EBean;

import pl.guideme.componentslib.FragmentComponentAdapter;
import pl.guideme.componentslib.ComponentContainer;
import pl.guideme.burkia.view.fragments.DrawerFragment;

@EBean
public class DrawerComponent extends FragmentComponentAdapter {
    private ToolbarComponent mToolbarComponent;
    private FragmentComponent mFragmentComponent;

    @Override
    public void onCreate(Context context, ComponentContainer componentContainer) {
        super.onCreate(context, componentContainer);
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

    @Override
    protected void onFragmentAction(Bundle actionArgument) {
        mToolbarComponent.animateHamburgerCross();
        mFragmentComponent.popBackStack();
    }

    public void show() {
        final DrawerFragment drawerFragment = new DrawerFragment();
        drawerFragment.attachToComponent(this);
        mFragmentComponent.change(drawerFragment, false);
    }

    private void onHamburgerClick() {
        //TODO implement it
    }
}
