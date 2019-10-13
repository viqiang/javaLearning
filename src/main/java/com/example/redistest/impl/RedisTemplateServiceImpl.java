package com.example.redistest.impl;

import com.alibaba.fastjson.JSON;
import com.example.redistest.service.IRedisTemplateService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisTemplateServiceImpl implements IRedisTemplateService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key,value);
    }

    @Override
    public void set(String key, String value, int expiretime) {
        stringRedisTemplate.opsForValue().set(key,value,expiretime,TimeUnit.SECONDS);
    }

    @Override
    public <T> void setList(String key, List<T> list) {
        set(key, JSON.toJSONString(list));
    }

    @Override
    public <T> void setList(String key, List<T> list, int expiretime) {
        set(key, JSON.toJSONString(list), expiretime);
    }

    @Override
    public void setObject(String key, Object object) {
        set(key,JSON.toJSONString(object));
    }

    @Override
    public void setObject(String key, Object object, int expiretime) {
        set(key,JSON.toJSONString(object), expiretime);
    }

    @Override
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public <T> List<T> getList(String key, Class<T> clazz) {
        String json = get(key);
        return JSON.parseArray(json, clazz);
    }

    /**
     * 获取多个redis值
     * @param keys
     * @return
     */
    @Override
    public List<String> multiGet(Collection<String> keys) {
        return stringRedisTemplate.opsForValue().multiGet(keys);
    }

    @Override
    public <T> List<T> multiGet(Collection<String> keys, Class<T> clazz) {
        List<String> values = multiGet(keys);
        List<T> result = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(values)) {
            values.forEach(v -> result.add(JSON.parseObject(v, clazz)));
        }
        return result;
    }

    @Override
    public <T> T getObject(String key, Class<T> clazz) {
        String json = get(key);
        return JSON.parseObject(json, clazz);
    }

    @Override
    public void expire(String key, int expiretime) {
        stringRedisTemplate.expire(key,expiretime, TimeUnit.SECONDS);
    }

    @Override
    public void remove(String key) {
        stringRedisTemplate.delete(key);
    }

    @Override
    public long generateKey(String key, int expire) {
        long increment = stringRedisTemplate.opsForValue().increment(key, 1);
        expire(key, expire);
        return increment;
    }

    @Override
    public Set<String> keys(String pattern) {
        return stringRedisTemplate.keys(pattern);
    }

    @Override
    public long incr(String key, long liveTime) {
        RedisAtomicLong redisAtomicLong = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        long increment = redisAtomicLong.getAndIncrement();
        if (liveTime > 0) {
            redisAtomicLong.expire(liveTime, TimeUnit.SECONDS);
        }
        return increment + 1;
    }

    @Override
    public void ConverAndSend(String channel, Object message) {
        Assert.hasText(channel, "Channel name must not be null or empty!");
        Assert.notNull(message, "Message must not be null!");
        stringRedisTemplate.convertAndSend(channel, message);
    }
}
