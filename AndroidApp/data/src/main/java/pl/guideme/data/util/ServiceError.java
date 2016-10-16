package pl.guideme.data.util;

public class ServiceError {
    private int code;
    private String message;
    private int httpCode;

    public ServiceError(int code, String message, int httpCode) {
        this.code = code;
        this.message = message;
        this.httpCode = httpCode;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public int getHttpCode() {
        return httpCode;
    }
}
