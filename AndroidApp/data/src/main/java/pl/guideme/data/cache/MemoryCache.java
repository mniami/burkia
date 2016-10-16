package pl.guideme.data.cache;

import org.androidannotations.annotations.EBean;

@EBean(scope = EBean.Scope.Singleton)
public class MemoryCache {
    private Object mCachedObject;

    public Object getCachedObject() {
        return mCachedObject;
    }

    public void setCachedObject(Object cachedObject) {
        mCachedObject = cachedObject;
    }
}
