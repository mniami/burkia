package pl.guideme.componentslib;

import android.os.Bundle;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.robolectric.util.ReflectionHelpers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BaseFragmentTest extends RobolectricTest{
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
        assertFalse(mBaseFragment.mDestroyed);
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
        assertFalse(mBaseFragment.mDestroyed);
        assertNotNull(mBaseFragment.mReferencedComponent);
        assertEquals(mBaseFragment.mReferencedComponent.get(), mFirstComponent);
    }

    @Test
    public void testOnStart() throws Exception {
        prepareFragment();

        mBaseFragment.onStart();

        verify(mFirstComponent).onFragmentAction(mBaseFragment, FragmentAction.Started);
    }

    @Test
    public void testOnResume() throws Exception {
        prepareFragment();

        mBaseFragment.onResume();

        verify(mFirstComponent).onFragmentAction(mBaseFragment, FragmentAction.Resumed);
    }

    @Test
    public void testOnSaveInstanceState() throws Exception {
        //given
        prepareFragment();
        Bundle savedInstanceState = mock(Bundle.class);
        mBaseFragment.attachToComponent(mFirstComponent);

        //when
        mBaseFragment.onSaveInstanceState(savedInstanceState);

        //verify
        verify(savedInstanceState).putInt(ActionKeys.COMPONENT_ID, mFirstComponent.getComponentId());
    }

    @Test
    public void testOnPause() throws Exception {
        prepareFragment();

        mBaseFragment.onPause();

        verify(mFirstComponent).onFragmentAction(mBaseFragment, FragmentAction.Paused);
    }

    @Test
    public void testOnStop() throws Exception {
        prepareFragment();

        mBaseFragment.onStop();

        verify(mFirstComponent).onFragmentAction(mBaseFragment, FragmentAction.Stopped);
    }

    @Test
    public void testOnDestroyView() throws Exception {
        prepareFragment();

        mBaseFragment.onDestroyView();

        assertNull(mBaseFragment.mView);
        assertTrue(mBaseFragment.mDestroyed);
    }

    @Test
    public void testOnDestroy() throws Exception {
        mActivityController.setup();
        prepareFragment();

        mBaseFragment.onDestroyView();
        assertNotNull(mBaseFragment.mView);

        mBaseFragment.onDestroy();

        verify(mFirstComponent).onFragmentAction(mBaseFragment, FragmentAction.Destroyed);
        assertNull(mBaseFragment.mView);
    }

    @Test
    public void testSetFragmentListener() throws Exception {

    }

    @Test
    public void testRaiseAction() throws Exception {

    }

    private void prepareFragment() {
        replaceFragment(mBaseFragment);
        mBaseFragment.attachToComponent(mFirstComponent);
    }
}