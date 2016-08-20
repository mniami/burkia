package pl.guideme.componentslib;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk=16, constants = BuildConfig.class)
public class RobolectricTest {
    protected Context mContext;
    protected FragmentActivity mActivity;
    private ActivityController<FragmentActivity> mActivityController;

    @Before
    public void setUp() throws Exception {
        mActivityController = Robolectric.buildActivity(FragmentActivity.class);
        mContext = mActivityController.get().getApplicationContext();
        mActivity = mActivityController.get();
    }

    @After
    public void tearDown() throws Exception {
        if (mActivityController != null) {
            mActivityController.destroy();
            mActivityController = null;
        }
        mContext = null;
    }

}
