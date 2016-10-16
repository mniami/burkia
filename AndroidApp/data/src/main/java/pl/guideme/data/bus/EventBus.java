package pl.guideme.data.bus;

import org.androidannotations.annotations.EBean;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@EBean(scope = EBean.Scope.Singleton)
public class EventBus {
    private Map<Object, List<Subscriber>> mMap = new HashMap<>();
    private final Object sync = new Object();

    public void publish(EventAction eventAction) {
        synchronized (sync) {
            List<Subscriber> subscribers = mMap.get(eventAction.getKey());
            if (subscribers != null) {
                for (Subscriber subscriber : subscribers) {
                    subscriber.onSubscription(eventAction);
                }
            }
        }
    }

    public void subscribe(Object key, Subscriber subscriber){
        synchronized (sync) {
            if (!mMap.containsKey(key)) {
                List<Subscriber> list = new LinkedList<>();
                mMap.put(key, list);
                list.add(subscriber);
            }
            else {
                mMap.get(key).add(subscriber);
            }
        }
    }

    public void unSubscribe(Object key, Subscriber subscriber){
        synchronized (sync){
            if (mMap.containsKey(key)){
                List<Subscriber> subscribers = mMap.get(key);
                if (subscribers != null){
                    subscribers.remove(subscriber);
                }
                if (subscribers.size() == 0){
                    mMap.remove(key);
                }
            }
        }
    }
}
