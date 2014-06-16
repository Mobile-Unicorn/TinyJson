/*
 * Copyright (C) 2014 The TinyJson Project of ChangYou
 *
 * 本文件涉及代码允许在畅游公司的所属项目中使用
 */
package com.unicorn.tinyjson.core;

import java.io.StringReader;

import org.json.JSONObject;

import com.unicorn.tinyjson.internal.ModelAdapterFactory;
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
	private TypeAdapterFactory mFactory;
    /**
     * 
     */
    public JsonFacade() {
    	mFactory = new ModelAdapterFactory();
    	
    }

    /**
     * 
     * @param json
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
	public <T> T fromJson(String json) {
        T result = (T)mFactory.create().read(new StringReader(json));
        return result;
    }
    
}
