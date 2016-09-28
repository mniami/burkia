package pl.guideme.componentslib;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

@RunWith(ApplicationRunner.class)
@Config(sdk=16, manifest = Config.NONE)
public class RobolectricTest {
    private static final int CONTENT_FRAME_ID = 32734;
    protected Context mContext;
    protected Activity mActivity;
    protected ActivityController<Activity> mActivityController;
    protected FrameLayout mFrameLayout;

    @Before
    public void setUp() throws Exception {
        mActivityController = Robolectric.buildActivity(Activity.class);
        mContext = mActivityController.get().getApplicationContext();
        mActivity = mActivityController.get();

        mFrameLayout = new FrameLayout(mContext);
        mFrameLayout.setId(CONTENT_FRAME_ID);
        mActivity.setContentView(mFrameLayout);
    }

    @After
    public void tearDown() throws Exception {
        if (mActivityController != null && mActivity.isCreated()) {
            mActivityController.destroy();
            mActivityController = null;
        }
        mFrameLayout = null;
        mActivity = null;
        mContext = null;
    }

    protected void replaceFragment(Fragment fragment) {
        if (!mActivity.isCreated()){
            mActivityController.create();
        }
        mActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(mFrameLayout.getId(), fragment)
                .commit();
    }
}
