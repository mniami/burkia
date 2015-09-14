package com.restaurantmenu.restaurantmenu.activities;

import com.google.inject.Inject;
import com.restaurantmenu.restaurantmenu.contracts.Offer;
import com.restaurantmenu.restaurantmenu.services.datas.DataService;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by dszcz_000 on 15.08.2015.
 */
public class DataServiceWrap {
    private final ExecutorService service = Executors.newFixedThreadPool(3);
    private DataService dataService;
    private Logger logger;

    @Inject
    public DataServiceWrap(Logger logger, DataService dataService){
        this.logger = logger;
        this.dataService = dataService;
    }

    public List<Offer> getOffers(){
        return execute(new Callable<List<Offer>>() {
            @Override
            public List<Offer> call() throws Exception {
                return dataService.getOffers();
            }
        });
    }

    private <T> T execute(Callable<T> callable){
        try {
            return service.submit(callable).get();
        } catch (InterruptedException e) {
            if (logger.isLoggable(Level.WARNING)){
                logger.log(Level.WARNING, "Exection interrupted.", e);
            }
        } catch (ExecutionException e) {
            if (logger.isLoggable(Level.WARNING)){
                logger.log(Level.WARNING, "Execution failed", e);
            }
        }
        return null;
    }
}
