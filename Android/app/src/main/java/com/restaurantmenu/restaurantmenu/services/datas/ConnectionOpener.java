package com.restaurantmenu.restaurantmenu.services.datas;

import com.google.inject.Inject;
import com.restaurantmenu.restaurantmenu.core.EventAggregator;
import com.restaurantmenu.restaurantmenu.core.EventKeys;

import org.apache.thrift.transport.TTransportException;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by dszcz_000 on 18.08.2015.
 */
public class ConnectionOpener {
    private final RemoteService remoteService;
    private final Logger logger;
    private final Runnable runnable;
    private final ThreadFactory threadFactory;
    private ScheduledExecutorService service;
    private boolean isStarted;
    private boolean isOpened;

    @Inject
    public ConnectionOpener(Logger logger, RemoteService remoteService, final EventAggregator eventAggregator){
        this.logger = logger;
        this.remoteService = remoteService;
        this.runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    if (!ConnectionOpener.this.remoteService.isOpen()) {
                        ConnectionOpener.this.remoteService.close();
                        ConnectionOpener.this.remoteService.open();
                        isOpened = ConnectionOpener.this.remoteService.isOpen();
                        eventAggregator.get(EventKeys.RemoteConnectionEstablished).occured(null);
                    }
                } catch (IllegalStateException e) {
                    if (ConnectionOpener.this.logger.isLoggable(Level.FINE)){
                        ConnectionOpener.this.logger.log(Level.FINE, "Remote service. ", e);
                    }
                } catch (TTransportException e) {
                    isOpened = true;
                    if (ConnectionOpener.this.logger.isLoggable(Level.FINE)){
                        ConnectionOpener.this.logger.log(Level.FINE, "Remote service transport raised exception. ", e);
                    }
                }
            }
        };
        threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "ConnectionOpener");
            }
        };
    }

    public void forceReconnect(){
        if (logger.isLoggable(Level.FINE)){
            logger.fine("Force reconnect.");
        }
        if (remoteService.isOpen()){
            remoteService.close();
            this.isOpened = false;
        }
    }

    public boolean isConnectionOpened(){
        return isOpened;
    }

    public void open(){
        if (logger.isLoggable(Level.FINE)){
            logger.fine("Open called.");
        }
        if (isStarted){
            throw new IllegalStateException("Already opened");
        }
        isStarted = true;
        service = Executors.newScheduledThreadPool(1, threadFactory);
        service.scheduleAtFixedRate(runnable, 1000, 5000, TimeUnit.MILLISECONDS);
    }
    public void close(){
        if (logger.isLoggable(Level.FINE)){
            logger.fine("Close called.");
        }
        if (remoteService.isOpen()){
            remoteService.close();
        }
        service.shutdown();
        service = null;
        isStarted = false;
    }
}
