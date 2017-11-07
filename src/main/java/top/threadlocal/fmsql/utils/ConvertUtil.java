package top.threadlocal.fmsql.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConvertUtil {

    public static <T> Map<String, Object>[] listToMap(List<T> list){
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


}
