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

import pl.guideme.componentslib.util.L;

@EBean
public class BaseFragment extends Fragment {
    private static final L log = L.getL("BaseFragment");

    protected WeakReference<Component> mReferencedComponent;
    @Bean
    protected ComponentContainer mComponentContainer;

    protected boolean mDestroyed;
    protected View mView;

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        log.i("onCreate called");

        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            int componentId = savedInstanceState.getInt(ActionKeys.COMPONENT_ID, 0);
            if (componentId > 0) {
                attachToComponent(getComponent(componentId));
            }
        }
        executeAction(FragmentAction.Created);
        mDestroyed = false;
    }

    @Override
    public void onStart() {
        log.i("onStart called");
        super.onStart();
        executeAction(FragmentAction.Started);
    }

    @Override
    public void onResume() {
        log.i("onResume called");
        super.onResume();
        executeAction(FragmentAction.Resumed);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        log.i("onSaveInstanceState called");
        super.onSaveInstanceState(outState);
        if (mReferencedComponent != null && mReferencedComponent.get() != null && outState != null) {
            outState.putInt(ActionKeys.COMPONENT_ID, mReferencedComponent.get().getComponentId());
        }
    }

    @Override
    public void onPause() {
        log.i("onPause called");
        super.onPause();
        executeAction(FragmentAction.Paused);
    }

    @Override
    public void onStop() {
        log.i("onStop called");
        super.onStop();
        executeAction(FragmentAction.Stopped);
    }

    @Override
    public void onDestroyView() {
        log.i("onDestroyView called");
        super.onDestroyView();
        this.mView = getView();
        mDestroyed = true;
    }

    @Override
    public void onDestroy() {
        log.i("onDestroy called");
        super.onDestroy();
        removeViews(mView);
        executeAction(FragmentAction.Destroyed);
    }

    public void attachToComponent(Component component) {
        log.i("attachToComponent called");
        if (component != null) {
            mReferencedComponent = new WeakReference<>(component);
        }
    }

    protected void raiseAction(Bundle actionArguments) {
        log.i("raiseAction called with bundle");
        if (mReferencedComponent != null) {
            Component component = mReferencedComponent.get();
            if (component != null) {
                component.onFragmentAction(this, actionArguments);
            }
        }
    }

    private Component getComponent(int componentId) {
        if (mComponentContainer != null) {
            return mComponentContainer.getById(componentId);
        }
        return null;
    }

    private void executeAction(FragmentAction fragmentAction) {
        log.i("executeAction called with action " + fragmentAction);
        if (mReferencedComponent != null) {
            Component component = mReferencedComponent.get();
            if (component != null) {
                component.onFragmentAction(this, fragmentAction);
            }
        }
    }

    private void removeViews(View view) {
        log.i("removeViews called");
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
        this.mView = null;
    }
}
