package ru.bigbuzzy.monitor.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.bigbuzzy.monitor.model.config.Accept;
import ru.bigbuzzy.monitor.model.config.Resource;
import ru.bigbuzzy.monitor.model.config.Url;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: volodko
 * Date: 11.01.12
 * Time: 12:49
 */
@Repository
public class ResourceDao {
    private SimpleJdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new SimpleJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("resource")
                .usingGeneratedKeyColumns("id");
    }

    public void save(List<Resource> resources) {
        for (Resource resource : resources) {
            if (!exist(resource.getUrl().getPath())) {
                save(resource);
            }
        }
    }

    public void save(Resource resource) {
        Map<String, Object> parameters = new HashMap<String, Object>(11);
        parameters.put("email", resource.getEmail());

        Url url = resource.getUrl();
        parameters.put("login", url.getLogin());
        parameters.put("password", url.getPassword());
        parameters.put("path", url.getPath());
        parameters.put("host", url.getHost());
        parameters.put("port", url.getPort());
        parameters.put("protocol", url.getProtocol());

        Accept accept = resource.getAccept();
        parameters.put("response_code", accept.getResponseCode());
        parameters.put("response_size", accept.getResponseSize());
        parameters.put("connection_timeout", accept.getConnectionTimeout());
        parameters.put("socket_timeout", accept.getSocketTimeout());

        Number newId = jdbcInsert.executeAndReturnKey(parameters);
        resource.setId(newId.longValue());
    }

    private boolean exist(String path) {
        return jdbcTemplate.queryForInt("select count(*) from resource where path=?", path) > 0;
    }

    public List<Resource> findAll() {
        return this.jdbcTemplate.query(
                "select * from resource",
                new RowMapper<Resource>() {
                    public Resource mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Resource resource = new Resource();
                        resource.setId(rs.getLong("id"));
                        resource.setEmail(rs.getString("email"));

                        Accept accept = new Accept();
                        accept.setResponseCode(rs.getInt("response_code"));
                        accept.setResponseSize(rs.getLong("response_size"));
                        accept.setConnectionTimeout(rs.getInt("connection_timeout"));
                        accept.setSocketTimeout(rs.getInt("socket_timeout"));
                        resource.setAccept(accept);

                        Url url = new Url();
                        url.setLogin(rs.getString("login"));
                        url.setPassword(rs.getString("password"));
                        url.setPath(rs.getString("path"));

                        url.setHost(rs.getString("host"));
                        url.setPort(rs.getInt("port"));
                        url.setProtocol(rs.getString("protocol"));
                        resource.setUrl(url);
                        return resource;
                    }
                });
    }
}
