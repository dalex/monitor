package ru.bigbuzzy.monitor.model.config;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * User: volodko
 * Date: 05.12.11
 * Time: 15:58
 */
public class Url {
    private String login;
    private String password;
    private String path;

    private String host;
    private int port;
    private String protocol;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this).toString();
    }
}
