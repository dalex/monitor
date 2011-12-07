package ru.bigbuzzy.monitor.service;

import org.springframework.stereotype.Service;
import ru.bigbuzzy.monitor.model.task.ResourceStatus;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: volodko
 * Date: 06.12.11
 * Time: 21:21
 */
@Service
public class ResourceStatusService {

    private List<ResourceStatus> resourceStatuses = new LinkedList<ResourceStatus>();

    public synchronized void save(List<ResourceStatus> resourceStatuses) {
        this.resourceStatuses.addAll(resourceStatuses);
    }

    public synchronized void delete(List<ResourceStatus> resourceStatuses) {
        this.resourceStatuses.removeAll(resourceStatuses);
    }

    public synchronized List<ResourceStatus> getResourceStatuses() {
        return resourceStatuses;
    }
}