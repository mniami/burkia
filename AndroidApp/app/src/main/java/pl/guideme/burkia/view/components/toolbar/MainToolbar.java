package pl.guideme.burkia.view.components.toolbar;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.androidannotations.annotations.EBean;

import pl.guideme.burkia.R;
import pl.guideme.burkia.view.components.base.ComponentAdapter;

@EBean
public class MainToolbar extends ComponentAdapter {
    private Toolbar toolbar;
    private Hamburger hamburger;

    private View.OnClickListener hamburgerListener;

    public void setHamburgerListener(View.OnClickListener hamburgerListener) {
        this.hamburgerListener = hamburgerListener;
    }

    public void clearHamburgerListener() {
        this.hamburgerListener = null;
    }

    @Override
    public void onCreate(FragmentActivity activity, Context context, View view) {
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

    public void animateHamburgerCross() {

    }
}
