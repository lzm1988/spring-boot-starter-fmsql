package top.threadlocal.fmsql.utils;

import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.cglib.core.Converter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConvertUtil {

    public static Map<String, BeanCopier> copierMap = new HashMap<String,BeanCopier>();

    public static <S,T> String keyGenerator(S source, T target){

        return source.getClass().getName().hashCode()+"->"+target.getClass().getName().hashCode();
    }

    public static <T> Map<String, Object>[] listToMapArr(List<T> list){
        Map<String, Object>[] mapArr =null;
        if (null != list && list.size()>0){
            mapArr = new Map[list.size()];
            try {
                for(int i = 0;i<list.size();i++){
                    Map<String, Object> map = new HashMap<String, Object>();
                    Field[] fields = list.get(i).getClass().getDeclaredFields();
                    for (Field f : fields ) {
                        f.setAccessible(true);
                        Method method = list.get(i).getClass().getMethod(
                                "get"+f.getName().substring(0,1).toUpperCase()+f.getName().substring(1));

                        map.put(f.getName(),method.invoke(list.get(i)));
                    }
                    mapArr[i] = map;
                }
            }catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e){
                e.printStackTrace();
            }

        }
        return mapArr;
    }

    public static Map<String, Object> objToMap(Object paramObj, Map<String, Object> outMap){

        if (null == paramObj || null == outMap){
            throw new RuntimeException("input params is null");
        }

        BeanMap beanMap = BeanMap.create(paramObj);
        for (Object object : beanMap.keySet() ) {
            outMap.put(String.valueOf(object),beanMap.get(object));
        }
        //Field[] fields = paramObj.getClass().getDeclaredFields();
        //try {
        //    for (Field field : fields ) {
        //        String fieldName = field.getName();
        //        String getMethodName = "get"+field.getName().substring(0,1).toUpperCase()+field.getName().substring(1);
        //        Method method = paramObj.getClass().getMethod(getMethodName);
        //        outMap.put(fieldName,method.invoke(paramObj));
        //    }
        //} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
        //    e.printStackTrace();
        //}
        return outMap;

    }

    public static <T> T mapToObj(Map<String, Object> paramMap, T t){

        if (null == paramMap || null == t){
            throw new RuntimeException("input params is null");
        }
        BeanMap beanMap = BeanMap.create(t);
        beanMap.putAll(paramMap);
        return t;
    }

    //简单数值拷贝，不做数值转换
    public static <S,T> void beanCopyNoConverter(S source,T target){

        if (null == source || null == target){
            throw new RuntimeException("source obj or target obj is null");
        }
        String key = keyGenerator(source, target);
        BeanCopier copier = null;
        if(copierMap.containsKey(key)){
            copier = copierMap.get(key);
        }else{
            copier = BeanCopier.create(source.getClass(), target.getClass(),false);
            copierMap.put(key,copier);
        }

        copier.copy(source, target, null);

    }

    public static <S,T> void beanCopyWithConverter(S source,T target){
        if (null == source || null == target){
            throw new RuntimeException("source obj or target obj is null");
        }
        String key = keyGenerator(source, target);
        BeanCopier copier = null;
        if(copierMap.containsKey(key)){
            copier = copierMap.get(key);
        }else{
            copier = BeanCopier.create(source.getClass(), target.getClass(),true);
            copierMap.put(key,copier);
        }

        copier.copy(source, target, new Converter() {
            @Override
            public Object convert(Object value, Class target, Object context) {

                return value;
            }
        });
    }

}
