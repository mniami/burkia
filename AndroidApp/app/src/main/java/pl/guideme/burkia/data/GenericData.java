package pl.guideme.burkia.data;

import java.util.concurrent.locks.ReentrantLock;

public class GenericData<T> {
    private boolean alreadySet;
    private boolean locked;
    private T mData;
    private final Object synch = new Object();
    private final ReentrantLock locker = new ReentrantLock();

    public T get() {
        synchronized (synch) {
            return mData;
        }
    }

    public void set(T data) {
        synchronized (synch) {
            mData = data;
            alreadySet = true;
        }
    }

    public void reset(){
        synchronized (synch) {
            alreadySet = false;
        }
    }

    public boolean isSet() {
        return alreadySet;
    }

    public boolean isLocked(){
        synchronized (synch) {
            return locked;
        }
    }

    public void lock() {
        synchronized (synch){
            locked = true;
            locker.lock();
        }
    }
    public void unlock() {
        synchronized (synch){
            locked = false;
            locker.unlock();
        }
    }
    public void waitForUnlock(){
        locker.lock();
        try {
        } finally {
            locker.unlock();
        }
    }
}
