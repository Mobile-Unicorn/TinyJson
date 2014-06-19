/*
 * Copyright (C) 2014 The TinyJson Project of ChangYou
 *
 * 本文件涉及代码允许在畅游公司的所属项目中使用
 */
package com.unicorn.tinyjson.core;

import android.util.Log;

import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.unicorn.tinyjson.internal.ModelAdapterFactory;
import com.unicorn.tinyjson.internal.TypeAdapter;
import com.unicorn.tinyjson.internal.TypeAdapterFactory;

/**
 * Json门面类
 * <p>
 * 提供Json数据的操作接口
 * </p>
 * @author xuchunlei
 *
 */
public final class JsonFacade {
    
    private String TAG = "JsonFacade";
    
	private TypeAdapterFactory mFactory;
	
	//Json数据适配器缓存
	private final Map<TypeToken<?>, TypeAdapter<?>> mAdapterCache;
	
    /**
     * 
     */
    public JsonFacade() {
    	mFactory = new ModelAdapterFactory();
    	mAdapterCache = Collections.synchronizedMap(new HashMap<TypeToken<?>, TypeAdapter<?>>());
    }

    /**
     * 
     * @param json
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
	public <T> T fromJson(String json, Type type) {
        TypeToken<T> token = (TypeToken<T>)TypeToken.get(type);
        TypeAdapter<T> adapter = getAdapter(token);
        T result = adapter.read(new StringReader(json));
        return result;
    }
    
    @SuppressWarnings("unchecked")
    private <T> TypeAdapter<T> getAdapter(TypeToken<T> type) {
        TypeAdapter<?> cache = mAdapterCache.get(type);
        if(cache != null) {
            Log.i(TAG, "get adapter from cache");
            return (TypeAdapter<T>) cache;
        } else {
            Log.i(TAG, "create a new adapter");
            TypeAdapter<T> adapter = mFactory.create(type);
            mAdapterCache.put(type, adapter);
            return adapter;
        }
    }
}
