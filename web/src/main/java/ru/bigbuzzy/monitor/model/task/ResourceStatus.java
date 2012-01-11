package ru.bigbuzzy.monitor.model.task;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import ru.bigbuzzy.monitor.model.config.Resource;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: volodko
 * Date: 06.12.11
 * Time: 18:01
 */
public class ResourceStatus {
    private Long id;
    private Date createTime;
    private Resource resource;
    private int responseCode;
    private long responseSize;
    private long responseTimeOut;

    private boolean statusException;
    private String statusMessage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

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

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this).toString();
    }
}
