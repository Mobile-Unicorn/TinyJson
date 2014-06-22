/*
 * Copyright (C) 2014 The TinyJson Project of ChangYou
 *
 * 本文件涉及代码允许在畅游公司的所属项目中使用
 */
package com.unicorn.tinyjson;

import android.view.View;

import com.unicorn.tinyjson.annotation.Expose;
import com.unicorn.tinyjson.annotation.SerializedName;

import java.util.List;

/**
 * @author xuchunlei
 *
 */
public class ThemeModel {
    
	@Expose(deserialize = true)
    @SerializedName(value = "id")
    public int id;
    
    @Expose(deserialize = true)
    @SerializedName(value = "title")
    public String title;

    @Expose(deserialize = true)
    @SerializedName(value = "previews")
    public List<Preview> previews;
    
    public int position;
    
    
    
    /**
     * 
     */
    public ThemeModel() {
        
    }

    public static class Preview {
    	@Expose(deserialize = true)
        @SerializedName(value = "url")
    	String url;
    	
    	public Preview(String url) {
    		this.url = url;
    	}
    	
    	public Preview() {
    		
    	}
    }
}
