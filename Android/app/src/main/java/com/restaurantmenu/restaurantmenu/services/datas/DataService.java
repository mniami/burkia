package com.restaurantmenu.restaurantmenu.services.datas;

import com.google.inject.Inject;
import com.restaurantmenu.restaurantmenu.contracts.Offer;
import com.restaurantmenu.restaurantmenu.core.*;

import org.apache.thrift.TException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Damian on 2015-07-20.
 */
public class DataService implements IService {
    public final int CONNECTION_OPENED = 1;

    private Logger logger;
    private ConnectionOpener connectionOpener;
    private RemoteService remoteService;
    private DataCache dataCache;
    private EventAggregator eventAggregator;
    private final String ServiceName = "DataService";
    private Object offersCacheKey = new Object();
    private FreshDataChecker dataChecker = new FreshDataChecker(15 * 60 * 1000); // 15min

    @Inject
    public DataService(Logger logger, ConnectionOpener connectionOpener, RemoteService remoteService, DataCache dataCache, EventAggregator eventAggregator){
        this.logger = logger;
        this.connectionOpener = connectionOpener;
        this.remoteService = remoteService;
        this.dataCache = dataCache;
        this.eventAggregator = eventAggregator;
    }

    @Override
    public void execute(IAction action) {
        if (action instanceof StartAction){
            start();
        }
        if (action instanceof StopAction){
            stop();
        }
    }

    private void start() {
        if (logger.isLoggable(Level.INFO)){
            logger.log(Level.INFO, String.format("%s is started", ServiceName));
        }
        connectionOpener.open();
    }

    private void stop() {
        if (logger.isLoggable(Level.INFO)){
            logger.log(Level.INFO, String.format("%s is stopped", ServiceName));
        }
        connectionOpener.close();
    }

    public List<Offer> getOffers() {
        if (logger.isLoggable(Level.FINE)){
            logger.log(Level.FINE, String.format("GetOffers called.", ServiceName));
        }
        try {
            if (connectionOpener.isConnectionOpened()){
                DataCache.CacheItem cacheItem = dataCache.get(offersCacheKey);
                if (cacheItem != null && dataChecker.isFresh(cacheItem.addedTime)){
                    return (List<Offer>)cacheItem.value;
                }
                else {
                    List<Offer> loadedOffers = remoteService.getOffers();
                    if (loadedOffers != null) {
                        if (logger.isLoggable(Level.FINE)) {
                            logger.fine(String.format("Loaded %s offers", loadedOffers.size()));
                        }
                        dataCache.add(offersCacheKey, loadedOffers);
                        return loadedOffers;
                    }
                    return new ArrayList<>();
                }
            }
        } catch (TException e) {
            if (logger.isLoggable(Level.WARNING)){
                logger.log(Level.WARNING, "GetOffers failed", e);
            }
            connectionOpener.forceReconnect();
        }
        return new ArrayList<>();
    }

    public void forceRefresh(){
        dataCache.remove(offersCacheKey);
        getOffers();
    }
}
