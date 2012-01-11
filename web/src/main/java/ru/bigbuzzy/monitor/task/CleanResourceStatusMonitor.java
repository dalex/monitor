package ru.bigbuzzy.monitor.task;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import ru.bigbuzzy.monitor.model.task.ResourceStatus;
import ru.bigbuzzy.monitor.service.ResourceStatusService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: volodko
 * Date: 07.12.11
 * Time: 16:59
 */
public class CleanResourceStatusMonitor {

    @Autowired
    private ResourceStatusService resourceStatusService;

    private int expiredMillis;

    public CleanResourceStatusMonitor(int expiredMillis) {
        this.expiredMillis = expiredMillis;
    }

    public void execute() {
        DateTime currentDateTime = new DateTime().minusMillis(expiredMillis);
        Date expiredTime = currentDateTime.toDate();

        List<ResourceStatus> removeResourceStatuses = new ArrayList<ResourceStatus>();
        for (ResourceStatus resourceStatus : resourceStatusService.getResourceStatuses()) {
            if (resourceStatus.getCreateTime().before(expiredTime)) {
                removeResourceStatuses.add(resourceStatus);
            }
        }
        resourceStatusService.delete(removeResourceStatuses);
    }
}
