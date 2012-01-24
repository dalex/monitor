package ru.bigbuzzy.monitor.model.task;

import ru.bigbuzzy.monitor.model.config.Resource;

import java.util.LinkedList;
import java.util.List;

/**
  * User: volodko
 * Date: 17.01.12
 * Time: 12:14
  */
public class AssocResourceStatus {
    private Resource resource;

    private List<Status> statuses = new LinkedList<Status>();

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public List<Status> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<Status> statuses) {
        this.statuses = statuses;
    }

    public void addStatus(Status status) {
        statuses.add(status);
    }
}
