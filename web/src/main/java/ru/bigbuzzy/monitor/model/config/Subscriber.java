package ru.bigbuzzy.monitor.model.config;

/**
 * User: volodko
 * Date: 24.01.12
 * Time: 13:40
 */
public class Subscriber {
    private String name;
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        return email == null ? System.identityHashCode(this) : email.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (email == null) return false;
        if (!(o instanceof Subscriber)) return false;
        return email.equals(((Subscriber) o).email);
    }
}
