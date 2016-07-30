package pl.guideme.burkia.view.components;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.androidannotations.annotations.EBean;

import pl.guideme.burkia.R;
import pl.guideme.burkia.view.components.base.ComponentAdapter;
import pl.guideme.burkia.view.components.base.ComponentContainer;
import pl.guideme.burkia.view.customviews.Hamburger;

@EBean
public class ToolbarComponent extends ComponentAdapter {
    private Toolbar toolbar;
    private Hamburger hamburger;

    private View.OnClickListener hamburgerListener;

    @Override
    public void onCreate(FragmentActivity activity, Context context, View view, ComponentContainer componentContainer) {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        hamburger = (Hamburger) view.findViewById(R.id.hamburger);

        hamburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hamburgerListener != null) {
                    hamburgerListener.onClick(view);
                }
            }
        });
    }

    @Override
    public void onStop() {
        toolbar = null;
        hamburger = null;
    }

    public void setHamburgerListener(View.OnClickListener hamburgerListener) {
        this.hamburgerListener = hamburgerListener;
    }

    public void clearHamburgerListener() {
        this.hamburgerListener = null;
    }


    public void animateHamburgerCross() {

    }
}
