package pl.guideme.burkia.view.components;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import org.androidannotations.annotations.EBean;

import pl.guideme.componentslib.FragmentAction;
import pl.guideme.componentslib.FragmentComponentAdapter;
import pl.guideme.componentslib.ComponentContainer;
import pl.guideme.burkia.view.fragments.DrawerFragment;

@EBean
public class DrawerComponent extends FragmentComponentAdapter {
    private ToolbarComponent mToolbarComponent;
    private FragmentComponent mFragmentComponent;

    @Override
    public void initialize(Context context, ComponentContainer componentContainer) {
        super.initialize(context, componentContainer);
        mToolbarComponent = componentContainer.get(ToolbarComponent.class);
        mFragmentComponent = componentContainer.get(FragmentComponent.class);
    }

    private void onFragmentResumed() {
        mToolbarComponent.setHamburgerListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onHamburgerClick();
            }
        });
    }

    private void onFragmentPaused() {
        mToolbarComponent.clearHamburgerListener();
    }

    @Override
    public void onFragmentAction(Fragment fragment, Bundle actionArgument) {
        mToolbarComponent.animateHamburgerCross();
        mFragmentComponent.popBackStack();
    }

    @Override
    public void onFragmentAction(Fragment fragment, FragmentAction fragmentAction){
        if (fragmentAction == FragmentAction.Resumed){
            onFragmentResumed();
        }
        else if (fragmentAction == FragmentAction.Paused){
            onFragmentPaused();
        }
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
