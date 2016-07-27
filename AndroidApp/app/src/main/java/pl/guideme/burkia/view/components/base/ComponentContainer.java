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
            component.onCreate(activity, view.getContext(), view);
        }
    }

    public <T> T get(Class<T> itemClass) {
        for (Component component : components) {
            if (component.getClass() == itemClass){
                return (T) component;
            }
        }
        return null;
    }

    public void onResume() {
        for (Component component : components) {
            component.onResume();
        }
    }

    public void onPause() {
        for (Component component : components) {
            component.onPause();
        }
    }

    public void onDestroy() {
        for (Component component : components) {
            component.onDestroy();
        }
    }

    public void onStop() {
        for (Component component : components) {
            component.onStop();
        }
    }
}
