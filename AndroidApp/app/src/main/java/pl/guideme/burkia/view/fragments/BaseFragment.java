package pl.guideme.burkia.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment {
    private FragmentListener fragmentListener;

    public void setFragmentListener(FragmentListener listener){
        this.fragmentListener = listener;
    }
    public void clearFragmentListener(){
        this.fragmentListener = null;
    }

    @Override
    public void onResume(){
        super.onResume();
        if (fragmentListener != null){
            fragmentListener.fragmentResumed(this);
        }
    }
    @Override
    public void onStop(){
        super.onStop();
        if (fragmentListener != null){
            fragmentListener.fragmentStopped(this);
        }
    }
    @Override
    public void onStart(){
        super.onStart();
        if (fragmentListener != null){
            fragmentListener.fragmentStarted(this);
        }
    }
    @Override
    public void onPause(){
        super.onPause();
        if (fragmentListener != null){
            fragmentListener.fragmentPaused(this);
        }
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        if (fragmentListener != null){
            fragmentListener.fragmentDestroyed(this);
        }
        fragmentListener = null;
    }
}
