package pl.guideme.componentslib;

import android.os.Bundle;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BaseFragmentTest extends UnitTest{
    private static final int FIRST_COMPONENT_ID = 1;
    private static final int NO_COMPONENT_ID = 0;
    protected BaseFragment mBaseFragment;
    protected ComponentContainer mComponentContainer;
    protected Component mFirstComponent;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        mBaseFragment = new BaseFragment();
        mFirstComponent = mock(Component.class);
        mComponentContainer = ComponentContainer_.getInstance_(mContext);
        mComponentContainer.initialize(mActivity, new Component[]{
                mFirstComponent
        });
        mBaseFragment.mComponentContainer = mComponentContainer;
        when(mFirstComponent.getComponentId()).thenReturn(FIRST_COMPONENT_ID);
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testAttachToComponent() throws Exception {
        //when
        mBaseFragment.attachToComponent(mFirstComponent);

        //verify
        assertNotNull(mBaseFragment.mReferencedComponent);
        verify(mFirstComponent).register(mBaseFragment);
    }

    @Test
    public void testOnCreateAnimation() throws Exception {
        assertNull(mBaseFragment.onCreateAnimation(0, false, 0));
    }

    @Test
    public void testOnCreate_noAction() throws Exception {
        //given
        Bundle savedInstanceState = mock(Bundle.class);
        when(savedInstanceState.getInt(ActionKeys.COMPONENT_ID, 0)).thenReturn(NO_COMPONENT_ID);
        //when
        mBaseFragment.onCreate(savedInstanceState);
        //verify
        assertFalse(mBaseFragment.destroyed);
        assertNull(mBaseFragment.mReferencedComponent);
    }

    @Test
    public void testOnCreate_withAction() throws Exception {
        //given
        Bundle savedInstanceState = mock(Bundle.class);
        when(savedInstanceState.getInt(ActionKeys.COMPONENT_ID, 0)).thenReturn(FIRST_COMPONENT_ID);
        //when
        mBaseFragment.onCreate(savedInstanceState);
        //verify
        assertFalse(mBaseFragment.destroyed);
        assertNotNull(mBaseFragment.mReferencedComponent);
        assertEquals(mBaseFragment.mReferencedComponent.get(), mFirstComponent);
    }

    @Test
    public void testOnStart() throws Exception {

    }

    @Test
    public void testOnResume() throws Exception {

    }

    @Test
    public void testOnSaveInstanceState() throws Exception {

    }

    @Test
    public void testOnPause() throws Exception {

    }

    @Test
    public void testOnStop() throws Exception {

    }

    @Test
    public void testOnDestroyView() throws Exception {

    }

    @Test
    public void testOnDestroy() throws Exception {

    }

    @Test
    public void testSetFragmentListener() throws Exception {

    }

    @Test
    public void testRaiseAction() throws Exception {

    }
}