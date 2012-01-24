package ru.bigbuzzy.monitor.service;

import org.springframework.stereotype.Service;
import ru.bigbuzzy.monitor.model.config.Resource;
import ru.bigbuzzy.monitor.model.task.AssocResourceStatus;
import ru.bigbuzzy.monitor.model.task.Status;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * User: volodko
 * Date: 06.12.11
 * Time: 21:21
 */
@Service
public class ResourceStatusService {
    private Map<Resource, AssocResourceStatus> assocResourceStatusMap = new LinkedHashMap<Resource, AssocResourceStatus>();

    public synchronized void add(Resource resource, Status status) {
        AssocResourceStatus assocResourceStatus = assocResourceStatusMap.get(resource);
        if(assocResourceStatus == null) {
            assocResourceStatus = new AssocResourceStatus();
            assocResourceStatus.setResource(resource);
            assocResourceStatusMap.put(resource, assocResourceStatus);
        }
        assocResourceStatus.addStatus(status);
    }

    public synchronized void removeBeforeTime(Date toExpiredTime) {
        List<Status> removeStatuses = new ArrayList<Status>();
        for (AssocResourceStatus assocResourceStatus : assocResourceStatusMap.values()) {
            removeStatuses.clear();
            for (Status status : assocResourceStatus.getStatuses()) {
                if (status.getCreateTime().before(toExpiredTime)) {
                    removeStatuses.add(status);
                }
            }
            assocResourceStatus.getStatuses().removeAll(removeStatuses);
        }
    }

    public synchronized Collection<AssocResourceStatus> getAssocResourceStatuses() {
        return assocResourceStatusMap.values();
    }
}