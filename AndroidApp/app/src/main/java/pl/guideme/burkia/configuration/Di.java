package pl.guideme.burkia.configuration;

import java.util.HashMap;
import java.util.Map;

public class Di {
    private static Di di;

    static {
        di = new Di();
    }

    private Map<Object, Object> map = new HashMap<>();

    public static void setSingleton(Di di) {
        Di.di = di;
    }

    public static <T> T get(Class<T> instance) {
        return di.getInstance(instance);
    }

    public static <T, K> void set(Class<T> interfaceType, Class<K> implementationType) {
        di.map.put(interfaceType, implementationType);
    }

    private <T> T getInstance(Class<T> instance) {
        Object cachedObject = map.get(instance);
        if (cachedObject != null) {
            return (T) cachedObject;
        }
        return null;
    }
}
