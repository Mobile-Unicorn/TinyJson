/*
 * Copyright (C) 2014 The TinyJson Project of Unicorn
 *
 */
package com.unicorn.tinyjson.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.unicorn.tinyjson.internal.TypeAdapter;

/**
 * 上下文
 * <p>
 * 提供Json解析全局共用的资源
 * </p>
 * @author xu
 *
 */
public abstract class JsonContext {
	//Json数据适配器缓存
	protected final Map<TypeToken<?>, TypeAdapter<?>> mAdapterCache;
	
	protected JsonContext() {
		mAdapterCache = Collections.synchronizedMap(new HashMap<TypeToken<?>, TypeAdapter<?>>());
		
	}
	
	
    public abstract <T> TypeAdapter<T> getAdapter(TypeToken<T> type);
}
