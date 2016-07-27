package pl.guideme.burkia;

import org.androidannotations.annotations.EBean;

import pl.guideme.burkia.config.AppConfig;

@EBean(scope = EBean.Scope.Singleton)
public class AppConfigProvider {
    private AppConfig config;

    protected void initialize(AppConfig config){
        this.config = config;
    }

    public AppConfig getConfig(){
        return config;
    }
}
