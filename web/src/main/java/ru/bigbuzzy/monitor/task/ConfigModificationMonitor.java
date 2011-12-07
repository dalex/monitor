package ru.bigbuzzy.monitor.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ru.bigbuzzy.monitor.service.ConfigurationService;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: volodko
 * Date: 06.12.11
 * Time: 20:29
 */
public class ConfigModificationMonitor {
    private static final Logger logger = LoggerFactory.getLogger(ConfigModificationMonitor.class);

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    @Qualifier(value = "config")
    private String fileName;

    private long lastModification = -1;

    public void execute() {
        logger.trace("Method execute has started fileName={}", fileName);
        File file = new File(fileName);
        long lastModified = file.lastModified();
        if (lastModification != lastModified) {
            logger.trace("Reload config file name lastModification={}, currentModified={}", lastModification, lastModified);
            configurationService.load(fileName);
            lastModification = lastModified;
        }
    }
}
