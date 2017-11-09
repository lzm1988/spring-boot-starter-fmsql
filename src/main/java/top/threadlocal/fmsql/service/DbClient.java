package top.threadlocal.fmsql.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import top.threadlocal.fmsql.utils.ConvertUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "dbClient")
public class DbClient {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    FreemarkerService freemarkerService;

    @Autowired
    PropertiesSercice propertiesSercice;

    /**
     * 根据sql的唯一标识查询列表
     * @param sqlId
     * @param params
     * @param cls
     * @param <T>
     * @return
     */
    public <T> List<T> queryForList(String sqlId, Map<String,?> params,Class<T> cls){

        String sql = freemarkerService.getSql(sqlId,params);
        List<T> resultList = jdbcTemplate.query(sql,params,new BeanPropertyRowMapper<T>(cls));
        return resultList;
    }

    /**
     * 根据sql的唯一标识查询对象
     * @param sqlId
     * @param params
     * @param cls
     * @param <T>
     * @return
     */
    public <T> T queryForObject(String sqlId, Map<String,?> params,Class<T> cls){

        String sql  = freemarkerService.getSql(sqlId,params);
        T t = jdbcTemplate.queryForObject(sql,params, cls);
        return t;
    }

    /**
     * 根据sql的唯一标识执行sql,可以增删改
     * @param sqlId
     * @param params
     * @return
     */
    public int execute(String sqlId, Map<String,?> params){

        String sql = freemarkerService.getSql(sqlId,params);
        return jdbcTemplate.update(sql,params);
    }

    /**
     * 批量插入，参数为Map数组
     * @param sqlId
     * @param paramsArr
     * @return
     */
    public int[] batchInsert(String sqlId,Map<String,Object>[] paramsArr){

        Map<String,Object> param = new HashMap<String,Object>(1);
        if (null != paramsArr && paramsArr.length >0 && null != paramsArr[0]){
            param = paramsArr[0];
        }
        String sql = freemarkerService.getSql(sqlId,param);
        return jdbcTemplate.batchUpdate(sql, paramsArr != null ? paramsArr : new Map[0]);
    }

    /**
     * 批量插入，参数为List
     * @param sqlId
     * @param list
     * @param <T>
     * @return
     */
    public <T> int[] batchInsert(String sqlId, List<T> list){
        if (null == list){
            throw new RuntimeException("input list is null");
        }
        Map<String, Object>[] paramArr = ConvertUtil.listToMap(list);
        Map<String,Object> param = new HashMap<String,Object>(1);
        if (null != paramArr && paramArr.length >0 && null != paramArr[0]){
            param = paramArr[0];
        }
        String sql = freemarkerService.getSql(sqlId,param);
        return jdbcTemplate.batchUpdate(sql, paramArr != null ? paramArr : new Map[0]);
    }

    /**
     * 批量更新，参数为Map数组，每一条为一个Map对象
     * @param sqlId
     * @param paramsArr
     * @return
     */
    public int[] batchUpdate(String sqlId,Map<String,Object>[] paramsArr){
        Map<String,Object> param = new HashMap<String,Object>(1);
        if (null != paramsArr && paramsArr.length >0 && null != paramsArr[0]){
            param = paramsArr[0];
        }
        String sql = freemarkerService.getSql(sqlId,param);
        return jdbcTemplate.batchUpdate(sql, paramsArr != null ? paramsArr : new Map[0]);
    }

    /**
     * 批量更新，参数为List
     * @param sqlId
     * @param list
     * @return
     */
    public <T> int[] batchUpdate(String sqlId, List<T> list){

        if (null == list){
            throw new RuntimeException("input list is null");
        }
        Map<String, Object>[] paramArr = ConvertUtil.listToMap(list);
        Map<String,Object> param = new HashMap<String,Object>(1);
        if (null != paramArr && paramArr.length >0 && null != paramArr[0]){
            param = paramArr[0];
        }
        String sql = freemarkerService.getSql(sqlId,param);
        return jdbcTemplate.batchUpdate(sql, paramArr != null ? paramArr : new Map[0]);
    }

}
