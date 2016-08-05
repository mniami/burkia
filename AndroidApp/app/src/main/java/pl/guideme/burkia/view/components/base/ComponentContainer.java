package pl.guideme.burkia.view.components.base;

import android.support.v4.app.FragmentActivity;
import android.view.View;

import org.androidannotations.annotations.EBean;

@EBean
public class ComponentContainer {
    protected FragmentActivity activity;
    protected Component[] components;
    protected View view;

    public void onCreate(FragmentActivity activity, View view, Component... components) {
        this.activity = activity;
        this.components = components;
        this.view = view;

        for (Component component : components) {
            component.onCreate(activity.getApplicationContext(), this);
        }
    }

    public <T> T get(Class<T> itemClass) {
        for (Component component : components) {
            Class claz = component.getClass();
            if (itemClass.isAssignableFrom(claz)) {
                return (T) component;
            }
        }
        return null;
    }

    public void onDestroy() {
        for (Component component : components) {
            component.onDestroy();
        }
    }

    public Component getById(int componentId) {
        for (Component component : components) {
            if (component.getComponentId() == componentId) {
                return component;
            }
        }
        return null;
    }

    public boolean isEmpty() {
        return components == null;
    }
}
