package ru.bigbuzzy.monitor;

import org.eclipse.jetty.plus.jndi.Resource;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.webapp.WebAppContext;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: volodko
 * Date: 06.12.11
 * Time: 19:18
 */
public class RunMonitor {

    private static String DEFAULT_PORT = "8080";
    private static String DEFAULT_CONTEXT = "/";

    private static String PORT_KEY = "jetty.embedded.port";
    private static String CONTEXT_KEY = "jetty.embedded.context";

    private static Server server;

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage java RunMonitor /...path.../config.xml /...path.../console.properties");
            return;
        }

        Properties props = new Properties();
        try {
            props.load(new FileReader(args[1]));
        } catch (IOException e) {
            System.out.println("File properties " + args[1] + " was not read. Continue... ");
        }

        String warName = findWarName();
        if (warName == null) {
            System.out.println("War file not found. Exit...");
        }

        try {
            server = new Server();

            final Connector connector = new SelectChannelConnector();
            connector.setPort(Integer.valueOf(props.getProperty(PORT_KEY, DEFAULT_PORT)));
            server.setConnectors(new Connector[]{connector});

            final WebAppContext webappcontext = new WebAppContext();
            webappcontext.setContextPath(props.getProperty(CONTEXT_KEY, DEFAULT_CONTEXT));
            webappcontext.setWar(warName);

            server.setHandler(webappcontext);
            new Resource("java:comp/env/monitoring/config", args[0]);

            server.start();
            server.join();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stop() throws Exception {
        if(server != null) {
            server.stop();
        }
    }

    private static String findWarName() {
        String classPath = System.getProperty("java.class.path");
        String[] pathElements = classPath.split(System.getProperty("path.separator"));

        for (String pathElement : pathElements) {
            File file = new File(pathElement);
            if (!file.isDirectory()) {
                file = file.getParentFile();
            }

            if (file.isDirectory()) {
                String warName = findWarNameInDirectory(file);
                if (warName != null) {
                    return warName;
                }
            }
        }
        return null;
    }

    private static String findWarNameInDirectory(File directory) {
        for (File file : directory.listFiles()) {
            if (file.getAbsolutePath().toLowerCase().endsWith(".war")) {
                return file.getAbsolutePath();
            }
        }
        return null;
    }

}
