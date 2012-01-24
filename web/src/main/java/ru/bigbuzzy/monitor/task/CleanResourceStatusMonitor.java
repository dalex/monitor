package ru.bigbuzzy.monitor.task;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import ru.bigbuzzy.monitor.service.ResourceStatusService;

/**
 * User: volodko
 * Date: 07.12.11
 * Time: 16:59
 */
public class CleanResourceStatusMonitor {

    @Autowired
    private ResourceStatusService resourceStatusService;

    private int activeMillis;

    public CleanResourceStatusMonitor(int activeMillis) {
        this.activeMillis = activeMillis;
    }

    public void execute() {
        DateTime activeDateTime = new DateTime().minusMillis(activeMillis);
        resourceStatusService.removeBeforeTime(activeDateTime.toDate());
    }
}
