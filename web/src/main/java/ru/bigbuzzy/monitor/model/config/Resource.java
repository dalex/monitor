package ru.bigbuzzy.monitor.model.config;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: volodko
 * Date: 05.12.11
 * Time: 15:57
 */
public class Resource {
    private Url url;
    private Set<Subscriber> subscribers;
    private Accept accept;

    public Url getUrl() {
        return url;
    }

    public void setUrl(Url url) {
        this.url = url;
    }

    public Set<Subscriber> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Set<Subscriber> subscribers) {
        this.subscribers = subscribers;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (getUrl() == null) return false;
        if (!(o instanceof Resource)) return false;
        return getUrl().equals(((Resource) o).getUrl());
    }
}
