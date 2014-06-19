/*
 * Copyright (C) 2014 The TinyJson Project of ChangYou
 *
 * 本文件涉及代码允许在畅游公司的所属项目中使用
 */
package com.unicorn.tinyjson;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.unicorn.tinyjson.StringList.StringListInner;
import com.unicorn.tinyjson.ThemeModel.Previews;
import com.unicorn.tinyjson.core.JsonFacade;
import com.unicorn.tinyjson.core.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xuchunlei
 *
 */
public final class MainActivity extends Activity {
    
    private String TAG = "TinyJson";
    
    private final String HTTP_URL = "http://api.c-launcher.com/client/newtheme/lastest.do?pageSize=2&density=160";
    
    private List<ThemeModel> mThemes;
    
    private JsonFacade mFacade;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mFacade = new JsonFacade();
        
        
        Type type = StringList2.class.getGenericSuperclass();
        System.out.println(type); 
        ParameterizedType pt = (ParameterizedType) type;
        Type ownerType = pt.getOwnerType();
        Log.e(TAG, "ownerType is " + ownerType);
        
    }
    
    static class StringList2<T> extends StringListInner<T> {
        
    }
    
    public void onClick(View v) {
        Log.e(TAG, "Fetch and parse json data from server");
//        mFacade.fromJson("a test json string", ThemeModel.class);
//        mFacade.fromJson("a test json string", new TypeToken<List<ThemeModel>>() {}.getType());
        mFacade.fromJson("a test json string", new TypeToken<Previews>() {}.getType());
//        mFacade.fromJson("a test json string", ThemeModel[].class);
//        mFacade.fromJson("a test json string", new TypeToken<GenericModel<String>>() {}.getType());
//        mFacade.fromJson("a test json string", new TypeToken<GenericModel<ThemeModel> []>(){}.getType());
    }
   
}
