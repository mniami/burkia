package com.restaurantmenu.restaurantmenu.core;

/**
 * Created by Damian on 2015-07-20.
 */
public class ServiceHandler {
    private IService[] services;

    public ServiceHandler(IService[] services){
        this.services = services;
    }

    public void start(){
        executeAction(new StartAction());
    }

    public void stop(){
        executeAction(new StopAction());
    }

    private void executeAction(IAction action){
        for (IService service : services){
            service.execute(action);
        }
    }
}
