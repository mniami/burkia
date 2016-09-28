package pl.guideme.componentslib;

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
    void initialize(Context context, ComponentContainer container);

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
     * Call when activiy state has changed
     * @param activity
     * @param activityAction
     */
    void onActivityAction(Activity activity, ActivityAction activityAction);

    /**
     * Call by registered fragment to communicate fragment state
     * @param fragmentAction
     */
    void onFragmentAction(Fragment fragment, FragmentAction fragmentAction);

    /**
     * Call by registered fragment to communicate custom action executed
     * @param actionArguments
     */
    void onFragmentAction(Fragment fragment, Bundle actionArguments);
}
