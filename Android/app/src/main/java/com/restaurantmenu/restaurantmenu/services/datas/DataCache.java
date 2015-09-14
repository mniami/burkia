package com.restaurantmenu.restaurantmenu.services.datas;

import com.restaurantmenu.restaurantmenu.contracts.Offer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dszcz_000 on 18.08.2015.
 */
public class DataCache {
    public class CacheItem {
        public long addedTime;
        public Object value;

        public CacheItem(long addedTime, Object value){
            this.addedTime = addedTime;
            this.value = value;
        }
    }
    private Map<Object, CacheItem> cacheMap = new HashMap<Object, CacheItem>();

    public boolean isEmpty(Object dataKey){
        return !cacheMap.containsKey(dataKey);
    }

    public void add(Object dataKey, Object data){
        cacheMap.put(dataKey, new CacheItem(System.currentTimeMillis(), data));
    }

    public CacheItem get(Object dataKey){
        return cacheMap.get(dataKey);
    }

    public void remove(Object dataKey){
        cacheMap.remove(dataKey);
    }
}
