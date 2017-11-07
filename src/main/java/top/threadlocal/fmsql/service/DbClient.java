package top.threadlocal.fmsql.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "dbClient")
public class DbClient {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    FreemarkerService freemarkerService;

    public <T> List<T> queryForList(String sqlId, Map<String,?> params,Class<T> cls){

        String sql = freemarkerService.getSql(sqlId,params);
        List<T> resultList = jdbcTemplate.query(sql,params,new BeanPropertyRowMapper<T>(cls));
        return resultList;
    }

    public <T> T queryForObject(String sqlId, Map<String,?> params,Class<T> cls){

        String sql  = freemarkerService.getSql(sqlId,params);
        T t = jdbcTemplate.queryForObject(sql,params, cls);
        return t;
    }

    public int execute(String sqlId, Map<String,?> params){

        String sql = freemarkerService.getSql(sqlId,params);
        return jdbcTemplate.update(sql,params);
    }

    public int[] batchInsert(String sqlId,Map<String,Object>[] paramsArr){

        Map<String,Object> param = new HashMap<String,Object>(1);
        if (null != paramsArr && paramsArr.length >0 && null != paramsArr[0]){
            param = paramsArr[0];
        }
        String sql = freemarkerService.getSql(sqlId,param);
        return jdbcTemplate.batchUpdate(sql, paramsArr != null ? paramsArr : new Map[0]);
    }

    public int[] batchUpdate(String sqlId,Map<String,Object>[] paramsArr){
        Map<String,Object> param = new HashMap<String,Object>(1);
        if (null != paramsArr && paramsArr.length >0 && null != paramsArr[0]){
            param = paramsArr[0];
        }
        String sql = freemarkerService.getSql(sqlId,param);
        return jdbcTemplate.batchUpdate(sql, paramsArr != null ? paramsArr : new Map[0]);
    }

}
