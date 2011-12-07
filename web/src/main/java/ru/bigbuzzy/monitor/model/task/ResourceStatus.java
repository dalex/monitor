package ru.bigbuzzy.monitor.model.task;

import ru.bigbuzzy.monitor.model.config.Resource;

/**
 * Created by IntelliJ IDEA.
 * User: volodko
 * Date: 06.12.11
 * Time: 18:01
 */
public class ResourceStatus {
    private Resource resource;
    private int responseCode;
    private long responseSize;
    private long responseTimeOut;

    private boolean statusException;
    private String statusMessage;

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

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

    public boolean isStatusException() {
        return statusException;
    }

    public void setStatusException(boolean statusException) {
        this.statusException = statusException;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
}
