package ru.bigbuzzy.monitor.service;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.lang.Validate;
import org.springframework.stereotype.Service;
import ru.bigbuzzy.monitor.model.config.Accept;
import ru.bigbuzzy.monitor.model.config.Resource;
import ru.bigbuzzy.monitor.model.config.Url;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: volodko
 * Date: 05.12.11
 * Time: 16:05
 */
@Service
public class ConfigurationService {

    private List<Resource> resources;

    public synchronized void load(String fileName) {
        XMLConfiguration config = new XMLConfiguration();
        try {
            config.load(fileName);
            config.setThrowExceptionOnMissing(false);
            initResources(config);
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    private void initResources(XMLConfiguration config) {
        resources = new ArrayList<Resource>();
        int count = config.getMaxIndex("resources.resource");
        if (count >= 0) {
            Accept accept = new Accept();
            accept.setResponseCode(config.getInt("httpAccept.responseCode", -1));
            Validate.isTrue(accept.getResponseCode() != -1, "Http response code definition is undefined. Check configuration file");
            accept.setResponseSize(config.getLong("httpAccept.responseSize", -1));
            accept.setResponseTimeOut(config.getLong("httpAccept.responseTimeOut", -1));

            for (int i = 0; i <= count; ++i) {
                Resource resource = new Resource();

                Url url = new Url();
                url.setLogin(config.getString(String.format("resources.resource(%d).url[@login]", i)));
                url.setPassword(config.getString(String.format("resources.resource(%d).url[@password]", i)));

                String path = config.getString(String.format("resources.resource(%d).url", i));
                Validate.notEmpty(path, "Url definition is empty or undefined. Check configuration file");

                url.setPath(path);
                try {
                    URL netUrl = new URL(path);
                    url.setHost(netUrl.getHost());
                    url.setPort(netUrl.getPort());
                    url.setProtocol(netUrl.getProtocol());
                } catch (MalformedURLException e) {
                    Validate.notEmpty(url.getPath(), "Url definition is not correct. Check configuration file");
                }
                resource.setUrl(url);

                resource.setEmail(config.getString(String.format("resources.resource(%d).email", i)));
                Validate.notEmpty(resource.getEmail(), "Email in resource definition is undefined. Check configuration file");

                resource.setAccept(accept);
                resources.add(resource);
            }
        }
    }

    public synchronized List<Resource> getResources() {
        return resources;
    }
}
