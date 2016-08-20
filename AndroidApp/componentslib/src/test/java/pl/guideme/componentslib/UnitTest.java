package pl.guideme.componentslib;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import org.junit.After;
import org.junit.Before;

import static org.mockito.Mockito.mock;

public class UnitTest {
    protected Context mContext;
    protected FragmentActivity mActivity;

    @Before
    public void setUp() throws Exception {
        mContext = mock(Context.class);
        mActivity = mock(FragmentActivity.class);
    }

    @After
    public void tearDown() throws Exception {
        mContext = null;
        mActivity = null;
    }
}
