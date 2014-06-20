/*
 * Copyright (C) 2014 The TinyJson Project of Unicorn
 *
 */
package com.unicorn.tinyjson.internal;

import android.util.Log;

import com.unicorn.tinyjson.core.TypeToken;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 构造器工厂
 * <p>
 * 用于创建{@link Constructor}实例
 * </p>
 * @author xuchunlei
 *
 */
public final class ConstructorFactory {

    private String TAG = "ConstructorFactory";
    
    /**
     * 
     */
    public ConstructorFactory() {
        
    }

    public <T> ObjectConstructor<T> create(TypeToken<T> typeToken) {
        
        final Class<? super T> rawType = typeToken.getRawType();
        
        //普通类型
        ObjectConstructor<T> defaultConstructor = newDefaultConstructor(rawType);
        if (defaultConstructor != null) {
          return defaultConstructor;
        }
        
        return null;
    }
    
    /**
     * 创建默认的构造器
     * <p>
     * 用于创建普通类型
     * </p>
     * @param rawType
     * @return
     */
    private <T> ObjectConstructor<T> newDefaultConstructor(Class<? super T> rawType) {
        try {
            final Constructor<? super T> constructor = rawType.getDeclaredConstructor();
            if (!constructor.isAccessible()) {
                constructor.setAccessible(true);
            }
            return new ObjectConstructor<T>() {
                @SuppressWarnings("unchecked")
                public T construct() {
                    try {
                        Object[] args = null;
                        return (T) constructor.newInstance(args);
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to invoke " + constructor
                                + " with no args", e);
                    }
                }
            };
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "Failed to create constructor for " + rawType.toString());
            return null;
        }
    }
    
}
