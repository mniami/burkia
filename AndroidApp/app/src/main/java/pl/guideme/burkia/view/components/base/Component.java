package pl.guideme.burkia.view.components.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

/**
 * Component interface, base class in Component Oriented Programming Library.
 */
public interface Component {
    /**
     * OnCreate method calls when component was instantiated and attached to {@link ComponentContainer}.
     *
     * @param context
     * @param container
     */
    void onCreate(Context context, ComponentContainer container);

    /**
     * OnDestroy method calls when component is still attached to the {@link ComponentContainer} but is just
     * before detaching, destroying and recycling process.
     */
    void onDestroy();

    /**
     * Returns component ID unique for all components.
     * Fragments are recognizing component by this ID which has to be strictly unique in {@link ComponentContainer}.
     *
     * @return
     */
    int getComponentId();

    /**
     * Register fragment to component. This method is used by Fragments in {@link BaseFragment#onCreate(Bundle)} method.
     * Attach fragment to appropriate component using {@link ComponentContainer#getById(int)} method to find component stored in {@link Bundle}
     * assigned previously in {@link BaseFragment#onCreate(Bundle)} method.
     *
     * @param fragment
     */
    void register(Fragment fragment);

    /**
     * Call when connected activity is created.
     *
     * @param activity
     */
    void onActivityCreated(AppCompatActivity activity);

    /**
     * Call when connected activity is destroyed.
     *
     * @param activity
     */
    void onActivityDestroy(AppCompatActivity activity);
}
