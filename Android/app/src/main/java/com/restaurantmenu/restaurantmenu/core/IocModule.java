package com.restaurantmenu.restaurantmenu.core;

import com.google.inject.AbstractModule;
import com.restaurantmenu.restaurantmenu.activities.DataServiceWrap;
import com.restaurantmenu.restaurantmenu.activities.OfferAdapter;
import com.restaurantmenu.restaurantmenu.services.datas.ConnectionOpener;
import com.restaurantmenu.restaurantmenu.services.datas.DataService;
import com.restaurantmenu.restaurantmenu.services.datas.RemoteService;

/**
 * Created by Damian on 2015-07-27.
 */
public class IocModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(OfferAdapter.class).asEagerSingleton();
        bind(DataService.class).asEagerSingleton();
        bind(ImageLoader.class).asEagerSingleton();
        bind(MemoryCache.class).asEagerSingleton();
        bind(FileCache.class).asEagerSingleton();
        bind(DataServiceWrap.class).asEagerSingleton();
        bind(ConnectionOpener.class).asEagerSingleton();
        bind(EventAggregator.class).asEagerSingleton();
        bind(RemoteService.class).asEagerSingleton();
        bind(MainApplication.class).asEagerSingleton();
        bind(LocationProvider.class).asEagerSingleton();
    }
}
