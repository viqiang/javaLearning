package com.example.redistest.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.Collection;
import java.util.List;

@Slf4j
public class CommonUtil {

    public static <T> boolean  notEmpty(Collection<T>  collection) {
        return collection != null && !collection.isEmpty();
    }

    public static <T> T first(List<T> list) {
        if (notEmpty(list)) {
            return list.get(0);
        }
        return null;
    }


    /**
     * 对象深拷贝
     * @param source
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T deepClone(Object source, Class<T> clazz) {
        return JSONObject.parseObject(JSON.toJSONString(source), clazz);
    }

    public static <T> List<T> copyList(List source, Class<T> clazz) {
        return JSONObject.parseArray(JSON.toJSONString(source), clazz);
    }


    public static <T,D> List<D> copyList(Class<?> clazz, List<T> source) {
        List<D> list = Lists.newArrayList();
        for (T t : source) {
            try {
                Object target = clazz.newInstance();
                BeanUtils.copyProperties(t, target);
                list.add((D) target);
            } catch (IllegalAccessException  | InstantiationException e) {
                log.error("copyList error: {}", e);
            }
        }
        return list;
    }
}
