/*
 * Copyright (C) 2014 The TinyJson Project of ChangYou
 *
 * 本文件涉及代码允许在畅游公司的所属项目中使用
 */
package com.unicorn.tinyjson;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.unicorn.tinyjson.annotation.SerializedName;
import com.unicorn.tinyjson.core.JsonFacade;

import org.json.JSONObject;

import java.io.InputStreamReader;
import java.util.List;

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
    }
    
    public void onClick(View v) {
        Log.e(TAG, "Fetch and parse json data from server");
//        Log.e(TAG, "class is " + );
        
        
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(HTTP_URL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject obj) {
//                mThemes = mFacade.fromJson(obj.optJSONArray("themes"), mThemes.getclas);
                mThemes = mFacade.fromJson(obj.optJSONObject("themes"));
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                
            }
        });
        
        queue.add(request);
    }
}
