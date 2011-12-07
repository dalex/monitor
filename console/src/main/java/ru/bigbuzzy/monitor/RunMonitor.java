package ru.bigbuzzy.monitor;

import org.eclipse.jetty.plus.jndi.Resource;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: volodko
 * Date: 06.12.11
 * Time: 19:18
 */
public class RunMonitor {
    private static Logger logger = LoggerFactory.getLogger(RunMonitor.class);

    private static String getWarName() {
        String classPath = System.getProperty("java.class.path");
        String[] pathElements = classPath.split(System.getProperty("path.separator"));

        for(String pathElement : pathElements) {
            File file = new File(pathElement);
            if(!file.isDirectory()) {
               file = file.getParentFile();
            }

            if(file.isDirectory()) {
                String warName = getWarInDirectory(file);
                if(warName != null) {
                    return warName;
                }
            }
        }
        return null;
    }

    private static String getWarInDirectory(File directory) {
        for(File file : directory.listFiles()) {
            if(file.getAbsolutePath().toLowerCase().endsWith(".war")) {
                return file.getAbsolutePath();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        if(args.length != 1) {
            System.out.println("Usage java RunMonitor /...path.../config.xml");
            return;
        }

        String warName = getWarName();
        if(warName == null) {
            System.out.println("War file not found");
        }

        try {
            Server server = new Server();

            final Connector connector = new SelectChannelConnector();
            connector.setPort(9999);
            server.setConnectors(new Connector[]{connector});

            final WebAppContext webappcontext = new WebAppContext();
            webappcontext.setContextPath("/");
            webappcontext.setWar(warName);

            server.setHandler(webappcontext);
            new Resource("java:comp/env/config", args[0]);

            server.start();
            server.join();

        } catch (Exception e) {
            logger.error("Exception occurred: ", e);
        }
    }
}
