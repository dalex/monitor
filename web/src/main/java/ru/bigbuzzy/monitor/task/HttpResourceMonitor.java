package ru.bigbuzzy.monitor.task;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import ru.bigbuzzy.monitor.model.task.Status;
import ru.bigbuzzy.monitor.service.ConfigurationService;
import ru.bigbuzzy.monitor.service.ResourceStatusService;
import ru.bigbuzzy.monitor.model.config.Accept;
import ru.bigbuzzy.monitor.model.config.Resource;
import ru.bigbuzzy.monitor.model.config.Url;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

/**
 * User: volodko
 * Date: 05.12.11
 * Time: 14:37
 */
public class HttpResourceMonitor {
    private static final Logger logger = LoggerFactory.getLogger(HttpResourceMonitor.class);
    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private ResourceStatusService resourceStatusService;

    private ThreadSafeClientConnManager clientConnManager = new ThreadSafeClientConnManager();

    public void execute() {
        if (logger.isTraceEnabled()) {
            logger.trace("Method execute has started");
        }

        if (CollectionUtils.isEmpty(configurationService.getResources())) {
            return;
        }
        logger.trace("Resource size = {}", configurationService.getResources().size());

        for (Resource resource : configurationService.getResources()) {
            Accept accept = resource.getAccept();

            Status status = new Status();
            status.setCreateTime(new Date());

            try {
                DefaultHttpClient httpClient = new DefaultHttpClient(clientConnManager);
                HttpParams params = httpClient.getParams();
                HttpConnectionParams.setConnectionTimeout(params, accept.getConnectionTimeout());
                HttpConnectionParams.setSoTimeout(params, accept.getSocketTimeout());
                logger.trace("Set timeout params connectionTimeout={}, socketTimeout={}",
                        accept.getConnectionTimeout(), accept.getSocketTimeout());

                Url url = resource.getUrl();

                if (StringUtils.isNotBlank(url.getLogin())) {
                    httpClient.getCredentialsProvider().setCredentials(
                            new AuthScope(url.getHost(), url.getPort()),
                            new UsernamePasswordCredentials(url.getLogin(), url.getPassword()));
                }

                HttpGet httpget = new HttpGet(resource.getUrl().getPath());

                logger.trace("Executing request {}", httpget.getURI());

                StopWatch watch = new StopWatch();
                watch.start();
                HttpResponse response = httpClient.execute(httpget);
                watch.stop();
                logger.trace("Total time work: {}", watch.getTotalTimeMillis());
                status.setResponseTimeOut(watch.getTotalTimeMillis());

                if (response.getStatusLine() != null) {
                    int statusCode = response.getStatusLine().getStatusCode();
                    logger.trace("Response status code: {}", statusCode);
                    status.setResponseCode(statusCode);
                }

                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String responseBody = EntityUtils.toString(entity);
                    status.setResponseSize(responseBody.length());
                }
            } catch (Exception e) {
                logger.error("Exception occurred: ", e);
                status.setExceptionable(true);
                status.setExceptionShortMessage(e.getMessage());

                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                status.setExceptionFullMessage(sw.toString());
            }
            resourceStatusService.add(resource, status);
        }
    }

    public void destroy() {
        if (clientConnManager != null) {
            clientConnManager.shutdown();
        }
    }
}
