package org.weather_service_endpoint;

public class ErrorInfo {
    private final String url;
    private final String message;

    public ErrorInfo(String url, String message) {
        this.url = url;
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public String getMessage() {
        return message;
    }
}
