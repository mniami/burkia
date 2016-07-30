package pl.guideme.burkia.view.components.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.animation.Animation;

public class BaseFragment extends Fragment {
    protected FragmentListener mFragmentListener = new FragmentListenerAdapter();

    public void setFragmentListener(FragmentListener listener) {
        this.mFragmentListener = listener;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mFragmentListener != null) {
            mFragmentListener.fragmentResumed(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mFragmentListener != null) {
            mFragmentListener.fragmentStopped(this);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mFragmentListener != null) {
            mFragmentListener.fragmentStarted(this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mFragmentListener != null) {
            mFragmentListener.fragmentPaused(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if (mFragmentListener != null) {
                mFragmentListener.fragmentDestroyed(this);
            }
        } finally {
            mFragmentListener = null;
        }
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return null;
    }

    protected void raiseAction(Bundle actionArguments) {
        if (mFragmentListener != null) {
            mFragmentListener.fragmentAction(actionArguments);
        }
    }
}
