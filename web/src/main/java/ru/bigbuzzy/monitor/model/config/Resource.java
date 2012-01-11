package ru.bigbuzzy.monitor.model.config;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * Created by IntelliJ IDEA.
 * User: volodko
 * Date: 05.12.11
 * Time: 15:57
 */
public class Resource {
    private long id;
    private Url url;
    private String email;
    private Accept accept;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Url getUrl() {
        return url;
    }

    public void setUrl(Url url) {
        this.url = url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Accept getAccept() {
        return accept;
    }

    public void setAccept(Accept accept) {
        this.accept = accept;
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this).toString();
    }
}
