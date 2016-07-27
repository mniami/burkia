package pl.guideme.burkia;

import android.app.Application;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EApplication;

import pl.guideme.burkia.api.ApiService;
import pl.guideme.burkia.config.SharedAppConfig;

@EApplication
public class App extends Application {
    @Bean
    protected ApiService apiService;

    @Bean
    protected AppConfigProvider configProvider;

    @Override
    public void onCreate() {
        SharedAppConfig config = new SharedAppConfig(getApplicationContext());
        configProvider.initialize(config);
        apiService.initialize(config);
        super.onCreate();
    }
}
