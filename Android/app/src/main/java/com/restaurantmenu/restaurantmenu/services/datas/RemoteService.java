package com.restaurantmenu.restaurantmenu.services.datas;

/**
 * Created by dszcz_000 on 14.08.2015.
 */
import com.google.inject.Inject;
import com.restaurantmenu.restaurantmenu.contracts.Offer;
import com.restaurantmenu.restaurantmenu.contracts.OfferService;

import org.apache.http.client.ClientProtocolException;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RemoteService {
    private TTransport transport;
    private TProtocol protocol;
    private OfferService.Client client;
    private Logger logger;

    @Inject
    public RemoteService(Logger logger){
        this.logger = logger;
    }

    public void open() throws IllegalStateException, TTransportException {
        if (transport != null){
            throw new IllegalStateException("Connection already opened.");
        }
        //10.0.2.2 emulator
        transport = new TSocket("192.168.0.16", 9095, 5000);
        transport.open();

        protocol = new  TBinaryProtocol(transport);
        client = new OfferService.Client(protocol);

        if (logger.isLoggable(Level.FINE)){
            logger.fine("Remote service opened.");
        }
    }

    public boolean isOpen(){
        if (transport != null){
            return transport.isOpen();
        }
        return false;
    }

    public List<Offer> getOffers() throws TException {
        if (!isOpen()){
            throw new IllegalStateException("Service was not opened.");
        }
        return client.getOffers();
    }

    public void close(){
        if (transport != null) {
            if (transport.isOpen()) {
                transport.close();
            }
            transport = null;
        }
        if (logger.isLoggable(Level.FINE)){
            logger.fine("Remote service closed.");
        }
    }
}