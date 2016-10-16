package pl.guideme.burkia;

import android.app.Application;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EApplication;

import pl.guideme.burkia.config.SharedAppConfig;
import pl.guideme.data.net.ServerEngine;

@EApplication
public class App extends Application {
    @Bean
    protected ServerEngine mServerEngine;

    @Bean
    protected AppConfigProvider configProvider;

    @Override
    public void onCreate() {
        SharedAppConfig config = new SharedAppConfig(getApplicationContext());
        configProvider.initialize(config);
        mServerEngine.initialize(config.getApiUrl());
        super.onCreate();
    }
}
