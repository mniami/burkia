package pl.guideme.burkia.api;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import pl.guideme.burkia.BuildConfig;
import pl.guideme.burkia.config.AppConfig;
import pl.guideme.burkia.view.activities.MainActivity_;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk=16, constants = BuildConfig.class)
public class ApiServiceTest {
    private ApiService_ apiService;
    private AppConfig nonProperConfiguration;
    private AppConfig nullReturningConfiguration;
    private AppConfig properConfiguration;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        Context context = Robolectric.buildActivity(MainActivity_.class).get().getApplicationContext();
        apiService = ApiService_.getInstance_(context);
        nullReturningConfiguration = mock(AppConfig.class);
        when(nullReturningConfiguration.getApiUrl()).thenReturn(null);
        when(nonProperConfiguration.getApiUrl()).thenReturn("something");
        when(properConfiguration.getApiUrl()).thenReturn("http://mock.com");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testInitialize_noConfigurationForUrl() throws Exception {
        thrown.expect(IllegalStateException.class);
        thrown.reportMissingExceptionWithMessage("No Exception found");
        apiService.initialize(nullReturningConfiguration);
    }

    @Test
    public void testInitialize_alreadyInitialized() throws Exception {
        thrown.expect(IllegalStateException.class);
        thrown.reportMissingExceptionWithMessage("No Exception found");
        apiService.initialize(properConfiguration);
        apiService.initialize(properConfiguration);
    }
    @Test
    public void testInitialize_notProperUrl() throws Exception {
        thrown.expect(IllegalStateException.class);
        thrown.reportMissingExceptionWithMessage("No Exception found");
        apiService.initialize(nonProperConfiguration);
    }
    @Test
    public void testInitialize_initialized() throws Exception {
        apiService.initialize(properConfiguration);
        assertTrue(apiService.isInitialized());
    }

    @Test
    public void testFeed() throws Exception {

    }

    @Test
    public void testUserFeed() throws Exception {

    }

    @Test
    public void testSendPost() throws Exception {

    }
}