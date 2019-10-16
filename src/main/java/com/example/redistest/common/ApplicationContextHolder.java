package com.example.redistest.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextHolder implements ApplicationContextAware {

    private static volatile ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        ApplicationContextHolder.applicationContext = context;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }


    public static Object getBean(String beanName){
        return getApplicationContext().getBean(beanName);
    }

    public static boolean containsBean(String beanName) {
        return getApplicationContext().containsBean(beanName);
    }

    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }


    public static <T> T getBean(String beanName,Class<T> clazz) {
        return getApplicationContext().getBean(beanName, clazz);
    }


    /**
     * 获取环境变量值
     * @param key
     * @return
     */
    public static String getEnvProperty(String key) {
        return getApplicationContext().getEnvironment().getProperty(key);
    }

    public static Environment getEnv() {
        return getApplicationContext().getEnvironment();
    }


}
