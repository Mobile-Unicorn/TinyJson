/*
 * Copyright (C) 2014 The TinyJson Project of Unicorn
 *
 */
package com.unicorn.tinyjson.core;

import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.unicorn.tinyjson.internal.CollectionTypeAdapterFactory;
import com.unicorn.tinyjson.internal.ModelAdapterFactory;
import com.unicorn.tinyjson.internal.TypeAdapter;
import com.unicorn.tinyjson.internal.TypeAdapterFactory;
import com.unicorn.tinyjson.utils.FactoryMaker;

/**
 * Json门面类
 * <p>
 * 提供Json数据的操作接口
 * </p>
 * @author xuchunlei
 *
 */
public final class JsonFacade extends JsonContext {
    
    private String TAG = "JsonFacade";
    
	private final List<TypeAdapterFactory> mFactories;
	
    /**
     * 
     */
    public JsonFacade() {
    	List<TypeAdapterFactory> factories = new ArrayList<TypeAdapterFactory>();
    	factories.add(FactoryMaker.INTEGER_FACTORY);
    	factories.add(FactoryMaker.STRING_FACTORY);
    	factories.add(new CollectionTypeAdapterFactory(this));
    	factories.add(new ModelAdapterFactory(this));
    	mFactories = Collections.unmodifiableList(factories);
    }

    /**
     * 
     * @param json
     * @param clazz
     * @return
     * @throws IOException 
     */
    @SuppressWarnings("unchecked")
	public <T> T fromJson(String json, Type type) throws IOException {
        TypeToken<T> token = (TypeToken<T>)TypeToken.get(type);
        TypeAdapter<T> adapter = getAdapter(token);
        T result = adapter.read(new JsonReader(new StringReader(json)));
        return result;
    }

	@Override
	public <T> TypeAdapter<T> getAdapter(TypeToken<T> type) {
		TypeAdapter<?> cache = mAdapterCache.get(type);
        if(cache != null) {
            Log.i(TAG, "get adapter from cache");
            return (TypeAdapter<T>) cache;
        } else {
			Log.i(TAG, "create a new adapter");
			for (TypeAdapterFactory factory : mFactories) {
				TypeAdapter<T> adapter = factory.create(type);
				if (adapter != null) {
					mAdapterCache.put(type, adapter);
					return adapter;
				}
			}
			throw new IllegalArgumentException("TinyJson cannot handle " + type);
        }
	}
    
}
