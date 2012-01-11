package ru.bigbuzzy.monitor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bigbuzzy.monitor.dao.ResourceStatusDao;
import ru.bigbuzzy.monitor.model.task.ResourceStatus;

import java.util.LinkedList;
import java.util.List;

/**
 * User: volodko
 * Date: 06.12.11
 * Time: 21:21
 */
@Service
public class ResourceStatusService {

    @Autowired
    private ResourceStatusDao resourceStatusDao;

    @Transactional
    public void save(List<ResourceStatus> resourceStatuses) {
        resourceStatusDao.save(resourceStatuses);
    }

    @Transactional
    public void delete(List<ResourceStatus> resourceStatuses) {
        resourceStatusDao.delete(resourceStatuses);
    }

    @Transactional(readOnly = true)
    public List<ResourceStatus> getResourceStatuses() {
        return resourceStatusDao.findAll();
    }
}