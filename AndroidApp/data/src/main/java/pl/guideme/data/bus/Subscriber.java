package pl.guideme.data.bus;

public interface Subscriber {
    void onSubscription(EventAction eventAction);
}
