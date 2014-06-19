/*
 * Copyright (C) 2014 The TinyJson Project of ChangYou
 *
 * 本文件涉及代码允许在畅游公司的所属项目中使用
 */
package com.unicorn.tinyjson;

import android.view.View;

import com.unicorn.tinyjson.annotation.SerializedName;

import java.util.List;

/**
 * @author xuchunlei
 *
 */
public class ThemeModel {
    
    @SerializedName(value = "id")
    public int id;
    
    @SerializedName(value = "title")
    public String title;
    
    @SerializedName(value = "previews")
    public List<Previews> previews;
    
    /**
     * 
     */
    public ThemeModel() {
        
    }

    public class Previews extends GenericModel<String>{
        
    }
}
