package top.threadlocal.fmsql.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import top.threadlocal.fmsql.utils.ConvertUtil;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "dbClient")
public class DbClient {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    FreemarkerService freemarkerService;

    @Autowired
    PropertiesSercice propertiesSercice;

    public long insertGetId(String sqlId,Object paramObj){

        Map<String,Object> map = new HashMap<String, Object>();
        ConvertUtil.objToMap(paramObj,map);
        return insertGetId(sqlId,map);
    }

    public long insertGetId(String sqlId, Map<String,Object> params){

        String sql = freemarkerService.getSql(sqlId,params);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator preparedStatementCreator = con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            return ps;
        };

        jdbcTemplate.update(preparedStatementCreator, keyHolder);
        return keyHolder.getKey().longValue();
    }

    /**
     * 根据sql的唯一标识查询列表
     * @param sqlId
     * @param params
     * @param cls
     * @param <T>
     * @return
     */
    public <T> List<T> queryForList(String sqlId, Map<String,Object> params,Class<T> cls){

        String sql = freemarkerService.getSql(sqlId,params);
        List<T> resultList = namedParameterJdbcTemplate.query(sql,params,new BeanPropertyRowMapper<T>(cls));
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
    public <T> T queryForObject(String sqlId, Map<String,Object> params,Class<T> cls){

        String sql  = freemarkerService.getSql(sqlId,params);
        T t = namedParameterJdbcTemplate.queryForObject(sql,params, cls);
        return t;
    }

    /**
     * 根据sql的唯一标识执行sql,可以增删改
     * @param sqlId
     * @param params
     * @return
     */
    public int execute(String sqlId, Map<String,Object> params){

        String sql = freemarkerService.getSql(sqlId,params);
        return namedParameterJdbcTemplate.update(sql,params);
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
        if(null == sql || "".equals(sql)){
            throw new RuntimeException("sql must not be null!");
        }
        return namedParameterJdbcTemplate.batchUpdate(sql, paramsArr != null ? paramsArr : new Map[0]);
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
        Map<String, Object>[] paramArr = ConvertUtil.listToMapArr(list);
        Map<String,Object> param = new HashMap<String,Object>(1);
        if (null != paramArr && paramArr.length >0 && null != paramArr[0]){
            param = paramArr[0];
        }
        String sql = freemarkerService.getSql(sqlId,param);
        if(null == sql || "".equals(sql)){
            throw new RuntimeException("sql must not be null!");
        }
        return namedParameterJdbcTemplate.batchUpdate(sql, paramArr != null ? paramArr : new Map[0]);
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
        if(null == sql || "".equals(sql)){
            throw new RuntimeException("sql must not be null!");
        }
        return namedParameterJdbcTemplate.batchUpdate(sql, paramsArr != null ? paramsArr : new Map[0]);
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
        Map<String, Object>[] paramArr = ConvertUtil.listToMapArr(list);
        Map<String,Object> param = new HashMap<String,Object>(1);
        if (null != paramArr && paramArr.length >0 && null != paramArr[0]){
            param = paramArr[0];
        }
        String sql = freemarkerService.getSql(sqlId,param);
        if(null == sql || "".equals(sql)){
            throw new RuntimeException("sql must not be null!");
        }
        return namedParameterJdbcTemplate.batchUpdate(sql, paramArr != null ? paramArr : new Map[0]);
    }

}
