package ru.bigbuzzy.monitor.model.config;

/**
 * Created by IntelliJ IDEA.
 * User: volodko
 * Date: 05.12.11
 * Time: 20:21
 */
public class Accept {
    private int responseCode;
    private long responseSize;
    private int connectionTimeout;
    private int socketTimeout;


    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public long getResponseSize() {
        return responseSize;
    }

    public void setResponseSize(long responseSize) {
        this.responseSize = responseSize;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }
}
