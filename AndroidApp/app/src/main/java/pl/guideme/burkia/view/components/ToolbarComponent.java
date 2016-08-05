package pl.guideme.burkia.view.components;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.androidannotations.annotations.EBean;

import pl.guideme.burkia.R;
import pl.guideme.burkia.view.components.base.FragmentComponentAdapter;
import pl.guideme.burkia.view.customviews.Hamburger;

@EBean
public class ToolbarComponent extends FragmentComponentAdapter {
    private Toolbar toolbar;
    private Hamburger hamburger;
    private View.OnClickListener hamburgerListener;

    @Override
    public void onActivityCreated(AppCompatActivity activity) {
        toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        hamburger = (Hamburger) activity.findViewById(R.id.hamburger);

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
    public void onActivityDestroy(AppCompatActivity activity) {
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
        //TODO implement it
    }
}