package ru.bigbuzzy.monitor.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.bigbuzzy.monitor.model.config.Resource;
import ru.bigbuzzy.monitor.model.task.ResourceStatus;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: volodko
 * Date: 10.01.12
 * Time: 19:06
 */
@Repository
public class ResourceStatusDao {
    private SimpleJdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    private ResourceDao resourceDao;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new SimpleJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("resource_status")
                .usingGeneratedKeyColumns("id");
    }

    public void save(List<ResourceStatus> resourceStatuses) {
        for (ResourceStatus resourceStatus : resourceStatuses) {
            save(resourceStatus);
        }
    }

    public void save(ResourceStatus resourceStatus) {
        Map<String, Object> parameters = new HashMap<String, Object>(7);
        parameters.put("create_time", resourceStatus.getCreateTime());
        parameters.put("response_code", resourceStatus.getResponseCode());
        parameters.put("response_size", resourceStatus.getResponseSize());
        parameters.put("response_timeout", resourceStatus.getResponseTimeOut());
        parameters.put("is_status_exception", resourceStatus.isStatusException());
        parameters.put("status_message", resourceStatus.getStatusMessage());
        parameters.put("fk_resource", resourceStatus.getResource().getId());
        Number newId = jdbcInsert.executeAndReturnKey(parameters);
        resourceStatus.setId(newId.longValue());
    }

    public void delete(List<ResourceStatus> resourceStatuses) {
        for (ResourceStatus resourceStatus : resourceStatuses) {
            this.jdbcTemplate.update("delete from resource_status where id = ?", resourceStatus.getId());
        }
    }

    public List<ResourceStatus> findAll() {
        final Map<Long, Resource> resourceMap = new HashMap<Long, Resource>();
        for (Resource resource : resourceDao.findAll()) {
            resourceMap.put(resource.getId(), resource);
        }

        if (resourceMap.isEmpty()) {
            return Collections.emptyList();
        }

        return this.jdbcTemplate.query(
                "select * from resource_status order by create_time",
                new RowMapper<ResourceStatus>() {
                    public ResourceStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
                        ResourceStatus resourceStatus = new ResourceStatus();
                        resourceStatus.setId(rs.getLong("id"));
                        resourceStatus.setCreateTime(rs.getTimestamp("create_time"));
                        resourceStatus.setResponseCode(rs.getInt("response_code"));
                        resourceStatus.setResponseSize(rs.getLong("response_size"));
                        resourceStatus.setResponseTimeOut(rs.getLong("response_timeout"));
                        resourceStatus.setStatusException(rs.getBoolean("is_status_exception"));
                        resourceStatus.setStatusMessage(rs.getString("status_message"));
                        resourceStatus.setResource(resourceMap.get(rs.getLong("fk_resource")));
                        return resourceStatus;
                    }
                });
    }
}
