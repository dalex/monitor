package ru.bigbuzzy.monitor.service;

import org.springframework.stereotype.Service;
import ru.bigbuzzy.monitor.model.task.ResourceStatus;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: volodko
 * Date: 06.12.11
 * Time: 21:21
 */
@Service
public class ResourceStatusService {
    private List<ResourceStatus> resourceStatuses;

    public void save(List<ResourceStatus> resourceStatuses) {
        this.resourceStatuses = resourceStatuses;
    }

    public List<ResourceStatus> getResourceStatuses() {
        return resourceStatuses;
    }
}
