package pl.guideme.componentslib;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.lang.ref.WeakReference;

@EBean
public class BaseFragment extends Fragment {
    protected FragmentListener mFragmentListener;
    protected WeakReference<Component> mReferencedComponent;
    @Bean
    protected ComponentContainer mComponentContainer;

    protected boolean destroyed;
    protected View view;

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int componentId = savedInstanceState.getInt(ActionKeys.COMPONENT_ID, 0);
        if (componentId > 0) {
            attachToComponent(getComponent(componentId));
        }
        destroyed = false;
    }

    private Component getComponent(int componentId) {
        if (mComponentContainer != null) {
            return mComponentContainer.getById(componentId);
        }
        return null;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mFragmentListener != null) {
            mFragmentListener.fragmentStarted(this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mFragmentListener != null) {
            mFragmentListener.fragmentResumed(this);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mReferencedComponent != null && mReferencedComponent.get() != null) {
            outState.putInt(ActionKeys.COMPONENT_ID, mReferencedComponent.get().getComponentId());
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
    public void onStop() {
        super.onStop();
        if (mFragmentListener != null) {
            mFragmentListener.fragmentStopped(this);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.view = getView();
        destroyed = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        removeViews(view);
        try {
            if (mFragmentListener != null) {
                mFragmentListener.fragmentDestroyed(this);
            }
        } finally {
            mFragmentListener = null;
        }
    }

    public void attachToComponent(Component component) {
        if (component != null) {
            mReferencedComponent = new WeakReference<>(component);
            component.register(this);
        }
    }

    public void setFragmentListener(FragmentListener fragmentListener) {
        this.mFragmentListener = fragmentListener;
    }

    protected void raiseAction(Bundle actionArguments) {
        if (mFragmentListener != null) {
            mFragmentListener.fragmentAction(actionArguments);
        }
    }

    private void removeViews(View view) {
        if (view == null) {
            return;
        }
        if (view instanceof ViewGroup && !(view instanceof AdapterView)) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                removeViews(viewGroup.getChildAt(i));
            }
            viewGroup.removeAllViews();
        }
        if (view instanceof AdapterView) {
            ((AdapterView) view).setOnItemClickListener(null);
            ((AdapterView) view).setOnItemLongClickListener(null);
            ((AdapterView) view).setOnItemSelectedListener(null);
        } else {
            view.setOnClickListener(null);
        }
        view.setOnKeyListener(null);
        this.view = null;
    }
}
