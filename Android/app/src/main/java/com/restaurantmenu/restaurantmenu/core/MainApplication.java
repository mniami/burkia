package com.restaurantmenu.restaurantmenu.core;

import android.content.Context;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.restaurantmenu.restaurantmenu.services.datas.DataService;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Damian on 2015-07-20.
 */
public class MainApplication {
    private Context context;

    public interface MainApplicationListener {
        void onServicesStarted();
    }
    private static MainApplication instance = new MainApplication();

    public static MainApplication getInstance(){
        return instance;
    }

    private ServiceHandler serviceHandler;

    @Inject private DataService dataService;
    @Inject private LocationProvider locationProvider;

    private ApplicationState state = ApplicationState.nonStarted;
    private Injector injector;

    private List<MainApplicationListener> listeners = new ArrayList<MainApplicationListener>();

    public void addListener(MainApplicationListener listener){
        this.listeners.add(listener);
    }

    public void removeListener(MainApplicationListener listener){
        this.listeners.remove(listener);
    }

    public void start(Context context){
        if (state == ApplicationState.started){
            throw new IllegalStateException();
        }
        this.context = context;

        configureContainer();
        configureLogging();

        new Thread(new Runnable(){
            @Override
            public void run() {
                startServices();

                for (MainApplicationListener listener : listeners){
                    listener.onServicesStarted();
                }
            }
        }).start();
        state = ApplicationState.started;
    }

    public Context getApplicationContext(){
        return context;
    }

    private void configureContainer() {
        injector = Guice.createInjector(new IocModule());
    }


    private void configureLogging(){
        AndroidLoggingHandler.reset(new AndroidLoggingHandler());
        Logger.getLogger("").setLevel(Level.FINE);
    }

    private void startServices(){
        serviceHandler = new ServiceHandler(new IService[]{
                dataService,
                locationProvider
        });
        serviceHandler.start();
    }

    public ApplicationState getState(){
        return state;
    }

    public<T> T getInstance(Class<T> className) {
        return injector.getInstance(className);
    }
}
