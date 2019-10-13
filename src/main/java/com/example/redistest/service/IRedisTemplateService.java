package com.example.redistest.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface IRedisTemplateService {

    /**
     * 往redis存入数据（有默认的超时时间）
     * @param key
     * @param value
     */
    void set(String key, String value);

    void set(String key, String value, int expiretime);

    <T> void setList(String key, List<T> list);

    <T> void setList(String key, List<T> list, int expiretime);

    void setObject(String key, Object object);

    void setObject(String key, Object object, int expiretime);

    String get(String key);

    <T> List<T> getList(String key, Class<T> clazz);

    List<String> multiGet(Collection<String> keys);

    <T> List<T> multiGet(Collection<String> keys, Class<T> clazz);

    <T> T getObject(String key, Class<T> clazz);


    /**
     * 设置超时时间
     * @param key
     * @param expiretime
     */
    void expire(String key, int expiretime);

    /**
     * 删除redis数据
     * @param key
     */
    void remove(String key);

    /**
     * 获取数据库主键
     * @param key
     * @param expire
     * @return
     */
    long generateKey(String key, int expire);

    /**
     * 获取所有的key
     * @param pattern
     * @return
     */
    Set<String> keys(String pattern);

    /**
     * 获取redis自增序列
     * @param key
     * @param liveTime
     * @return
     */
    long incr(String key, long liveTime);

    /**
     * 发布消息
     * @param channel
     * @param message
     */
    void ConverAndSend(String channel, Object message);

}
