package ru.bigbuzzy.monitor.model.task;

import java.util.Date;

/**
 * User: volodko
 * Date: 06.12.11
 * Time: 18:01
 */
public class Status {
    private Date createTime;
    private int responseCode;
    private long responseSize;
    private long responseTimeOut;

    private boolean exceptionable;
    private String exceptionShortMessage;
    private String exceptionFullMessage;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public boolean isExceptionable() {
        return exceptionable;
    }

    public void setExceptionable(boolean exceptionable) {
        this.exceptionable = exceptionable;
    }

    public String getExceptionShortMessage() {
        return exceptionShortMessage;
    }

    public void setExceptionShortMessage(String exceptionShortMessage) {
        this.exceptionShortMessage = exceptionShortMessage;
    }

    public String getExceptionFullMessage() {
        return exceptionFullMessage;
    }

    public void setExceptionFullMessage(String exceptionFullMessage) {
        this.exceptionFullMessage = exceptionFullMessage;
    }
}
