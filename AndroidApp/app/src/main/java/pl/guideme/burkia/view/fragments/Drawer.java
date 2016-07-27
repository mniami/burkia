package pl.guideme.burkia.view.fragments;

import android.support.v4.app.Fragment;
import android.view.animation.Animation;

public class Drawer extends Fragment{
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return null;
    }

    public boolean closeClicked() {
        return false;
    }
}
