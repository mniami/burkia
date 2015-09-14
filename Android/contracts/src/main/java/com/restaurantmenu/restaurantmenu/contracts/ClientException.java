package com.restaurantmenu.restaurantmenu.contracts;

import org.apache.thrift.TException;

/**
 * Created by dszcz_000 on 19.08.2015.
 */
public class ClientException extends TException {
    public ClientException(Throwable th){
        super(th);
    }
}
