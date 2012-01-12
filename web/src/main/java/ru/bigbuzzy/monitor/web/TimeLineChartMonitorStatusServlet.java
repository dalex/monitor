package ru.bigbuzzy.monitor.web;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import ru.bigbuzzy.monitor.model.task.ResourceStatus;
import ru.bigbuzzy.monitor.service.ResourceStatusService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * User: volodko
 * Date: 11.01.12
 * Time: 16:06
 */
public class TimeLineChartMonitorStatusServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(TimeLineChartMonitorStatusServlet.class);

    private static final String PAGE_TEMPLATE = "timeLineChartMonitorServlet.ftl";

    private Configuration configuration;
    private ResourceStatusService resourceStatusService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(
                config.getServletContext());

        configuration = webApplicationContext.getAutowireCapableBeanFactory().getBean(Configuration.class);
        resourceStatusService = webApplicationContext.getAutowireCapableBeanFactory().getBean(ResourceStatusService.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF8");
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            logger.trace("Resource statuses size {}", resourceStatusService.getResourceStatuses().size());

            Map<String, List<ResourceStatus>> resourceStatusMap = new HashMap<String, List<ResourceStatus>>();
            for(ResourceStatus resourceStatus : resourceStatusService.getResourceStatuses()) {
                String urlKey = resourceStatus.getResource().getUrl().getPath();
                List<ResourceStatus> resourceStatuses = resourceStatusMap.get(urlKey);
                if(resourceStatuses == null) {
                    resourceStatuses = new LinkedList<ResourceStatus>();
                    resourceStatusMap.put(urlKey, resourceStatuses);
                }
                resourceStatuses.add(resourceStatus);
            }
            params.put("resourceStatusMap", resourceStatusMap);

            String autorefresh = request.getParameter("autorefresh");
            if(StringUtils.isNotEmpty(autorefresh)) {
                params.put("autorefresh", autorefresh);
            }

            response.getWriter().println(FreeMarkerTemplateUtils.processTemplateIntoString(
                    configuration.getTemplate(PAGE_TEMPLATE), params));
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (TemplateException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            logger.error("Exception occurred:", e);
        }
    }
}
