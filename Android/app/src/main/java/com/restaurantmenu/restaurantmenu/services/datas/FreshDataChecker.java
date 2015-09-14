package com.restaurantmenu.restaurantmenu.services.datas;

/**
 * Created by dszcz_000 on 18.08.2015.
 */
public class FreshDataChecker {
    private long maxFreshDelay;

    public FreshDataChecker(long maxFreshDelay){
        this.maxFreshDelay = maxFreshDelay;
    }

    public boolean isFresh(long time){
        return System.currentTimeMillis() - time <= maxFreshDelay;
    }
}
