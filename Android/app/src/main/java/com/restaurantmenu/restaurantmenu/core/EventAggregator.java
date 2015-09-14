package com.restaurantmenu.restaurantmenu.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dszcz_000 on 19.08.2015.
 */
public class EventAggregator {

    public class MySubscriber implements  ISubscriber {

        private List<ISubscriber> list;

        public MySubscriber(List<ISubscriber> list){
            this.list = list;
        }

        @Override
        public void occured(Object args) {
            for (ISubscriber s : list){
                s.occured(args);
            }
        }
    }

    private Map<Object, List<ISubscriber>> map = new HashMap<Object, List<ISubscriber>>();

    public void add(Object key, ISubscriber subscriber){
        List<ISubscriber> list = map.get(key);
        if (list == null){
            list = new ArrayList<ISubscriber>();
            map.put(key, list);
        }
        list.add(subscriber);
    }
    public void remove(Object key, ISubscriber subscriber) {
        List<ISubscriber> list = map.get(key);
        if (list == null) {
            list.remove(subscriber);
        }
    }

    public ISubscriber get(Object eventKey){
        List<ISubscriber> list = map.get(eventKey);
        if (list != null) {
            return new MySubscriber(list);
        }
        else {
            throw new IllegalArgumentException("Publisher doesnt exist.");
        }
    }
}
