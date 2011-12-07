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
    private long responseTimeOut;

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

    public long getResponseTimeOut() {
        return responseTimeOut;
    }

    public void setResponseTimeOut(long responseTimeOut) {
        this.responseTimeOut = responseTimeOut;
    }
}
