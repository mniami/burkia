package pl.guideme.data.util;

public interface ServiceCallback<T> {
    void success(T item);
    void failure(ServiceError serviceError);
}
