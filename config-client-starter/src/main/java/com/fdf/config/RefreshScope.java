package com.fdf.config;


import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;


/**
 * @author dongfengfan
 */
public class RefreshScope implements Scope {
    private static ConcurrentHashMap<String, Object> beans = new ConcurrentHashMap<>();

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        Object value = beans.get(name);
        if (value != null) {
            return value;
        }
        // 在创建新的对象
        Object object = objectFactory.getObject();
        beans.put(name, object);
        return object;
    }

    @Override
    public Object remove(String name) {
        return beans.remove(name);
    }

    @Override
    public void registerDestructionCallback(String s, Runnable runnable) {

    }

    @Override
    public Object resolveContextualObject(String s) {
        return null;
    }

    @Override
    public String getConversationId() {
        return null;
    }

    public static ConcurrentHashMap getBeans() {
        return beans;
    }
}