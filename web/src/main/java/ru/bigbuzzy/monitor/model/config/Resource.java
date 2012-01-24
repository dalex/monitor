package ru.bigbuzzy.monitor.model.config;

/**
 * Created by IntelliJ IDEA.
 * User: volodko
 * Date: 05.12.11
 * Time: 15:57
 */
public class Resource {
    private Url url;
    private String email;
    private Accept accept;

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
    public int hashCode() {
        return getUrl() == null ? System.identityHashCode(this) : getUrl().hashCode();
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (getUrl() == null) return false;
        if (!(o instanceof Resource)) return false;
        return getUrl().equals(((Resource) o).getUrl());
    }
}
