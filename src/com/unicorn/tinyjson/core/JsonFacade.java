/*
 * Copyright (C) 2014 The TinyJson Project of ChangYou
 *
 * 本文件涉及代码允许在畅游公司的所属项目中使用
 */
package com.unicorn.tinyjson.core;

import org.json.JSONObject;

/**
 * Json门面类
 * <p>
 * 提供Json数据的操作接口
 * </p>
 * @author xuchunlei
 *
 */
public final class JsonFacade {

    /**
     * 
     */
    public JsonFacade() {

    }

    /**
     * 
     * @param json
     * @param clazz
     * @return
     */
    public <T> T fromJson(JSONObject json) {
        T result = null;
        return result;
    }
    
}
