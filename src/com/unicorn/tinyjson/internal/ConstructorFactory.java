/*
 * Copyright (C) 2014 The TinyJson Project of Unicorn
 *
 */
package com.unicorn.tinyjson.internal;

import android.util.Log;

import com.unicorn.tinyjson.core.TypeToken;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * 构造器工厂
 * <p>
 * 用于创建{@link ObjectConstructor}实例
 * </p>
 * @author xuchunlei
 *
 */
public final class ConstructorFactory {

    private String TAG = "ConstructorFactory";
    
    private static final ConstructorFactory mInstance = new ConstructorFactory();
    
    /**
     * 
     */
    private ConstructorFactory() {
        
    }

    /**
     * 获取构造器工厂实例
     * @return
     */
    static ConstructorFactory getInstance() {
    	return mInstance;
    }
    
    public <T> ObjectConstructor<T> create(TypeToken<T> typeToken) {
        
        final Class<? super T> rawType = typeToken.getRawType();
        
        //普通类型
        ObjectConstructor<T> defaultConstructor = newDefaultConstructor(rawType);
        if (defaultConstructor != null) {
          return defaultConstructor;
        }
        
        //接口类型
        final Type type = typeToken.getType();
        ObjectConstructor<T> defaultImplementation = newDefaultImplementationConstructor(type, rawType);
        if (defaultImplementation != null) {
          return defaultImplementation;
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
    
    /**
     * 创建默认的构造器
     * <p>
     * 用于创建接口以及实现这些接口的类型，目前支持List及其子类型
     * </p>
     */
    @SuppressWarnings("unchecked") // use runtime checks to guarantee that 'T' is what it is
    private <T> ObjectConstructor<T> newDefaultImplementationConstructor(
        Type type, Class<? super T> rawType) {
      if (Collection.class.isAssignableFrom(rawType)) {
          return new ObjectConstructor<T>() {
            public T construct() {
              return (T) new ArrayList<Object>();
            }
          };
      }

      return null;
    }
    
}
