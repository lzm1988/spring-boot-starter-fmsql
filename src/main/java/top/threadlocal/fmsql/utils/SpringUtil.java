package top.threadlocal.fmsql.utils;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SpringUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        if (SpringUtil.applicationContext == null) {
            SpringUtil.applicationContext = arg0;
        }
    }

    // 获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    // 通过name获取 Bean.
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    // 通过class获取Bean.
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    // 通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

    public static <T> void dynamicAddBean(T t,String beanName, Map<String, ?> initParams){
        ApplicationContext ctx = SpringUtil.getApplicationContext();
        DefaultListableBeanFactory factory = (DefaultListableBeanFactory)ctx.getAutowireCapableBeanFactory();
        //获取BeanFactory
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) ctx.getAutowireCapableBeanFactory();

        //创建bean信息.
        Class cls = null;
        try {
            cls = Class.forName(t.toString().replace("class","").trim());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(cls);
        if (null != initParams){
            for (Map.Entry<String, ?> entry : initParams.entrySet() ) {
                beanDefinitionBuilder.addPropertyValue(entry.getKey(),entry.getValue());
            }
        }
        //动态注册bean.默认bean的名字是类名首字母小写
        if (beanName == null || "".equals(beanName)){
            String classAllName = t.toString();
            String[] classNameArr = classAllName.split("\\.");
            String className = classNameArr[classNameArr.length-1].substring(0,1).toLowerCase()+classNameArr[classNameArr.length-1].substring(1);
            defaultListableBeanFactory.registerBeanDefinition(className, beanDefinitionBuilder.getBeanDefinition());
        }else {
            defaultListableBeanFactory.registerBeanDefinition(beanName, beanDefinitionBuilder.getBeanDefinition());
        }
    }

    //默认bean名字
    public static <T> void dynamicAddBean(T t, Map<String, ?> initParams){
        dynamicAddBean(t,null,initParams);
    }

    public static <T> void dynamicAddBean(T t,String beanName){
        dynamicAddBean(t,beanName,null);
    }

    public static <T> void dynamicAddBean(T t){
        dynamicAddBean(t,null,null);
    }

    public static void dynamicDelBean(String beanName){
        ApplicationContext ctx = SpringUtil.getApplicationContext();
        DefaultListableBeanFactory factory = (DefaultListableBeanFactory)ctx.getAutowireCapableBeanFactory();
        //获取BeanFactory
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) ctx.getAutowireCapableBeanFactory();
        defaultListableBeanFactory.removeBeanDefinition(beanName);
    }


}
