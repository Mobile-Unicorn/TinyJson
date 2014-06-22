/*
 * Copyright (C) 2014 The TinyJson Project of Unicorn
 *
 */
package com.unicorn.tinyjson;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.unicorn.tinyjson.core.JsonFacade;
import com.unicorn.tinyjson.core.TypeToken;

import java.io.IOException;
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
    
    private Gson mGson;
    
//    private String testJson = "{\"id\":169848,\"title\":\"StoneAge\",\"previews\":[{\"url\":\"http://d.c-launcher.com/preview/348/53a0f6d4f48d3d6e2f521eac/desktop_mobile_1403163522660.jpg\"},{\"url\":\"http://d.c-launcher.com/preview/348/53a0f6d4f48d3d6e2f521eac/drawer_mobile_1403163522858.jpg\"},{\"url\":\"http://d.c-launcher.com/preview/348/53a0f6d4f48d3d6e2f521eac/widgets_mobile_1403163523080.jpg\"}]}";
//    private String testJson = "{\"id\":169848,\"title\":\"StoneAge\"}";
    private String testJson = "[{\"id\":169848,\"title\":\"StoneAge\",\"previews\":[{\"url\":\"http://d.c-launcher.com/preview/348/53a0f6d4f48d3d6e2f521eac/desktop_mobile_1403163522660.jpg\"},{\"url\":\"http://d.c-launcher.com/preview/348/53a0f6d4f48d3d6e2f521eac/drawer_mobile_1403163522858.jpg\"},{\"url\":\"http://d.c-launcher.com/preview/348/53a0f6d4f48d3d6e2f521eac/widgets_mobile_1403163523080.jpg\"}]},{\"id\":160390,\"title\":\"Rain Drops\",\"previews\":[{\"url\":\"http://d.c-launcher.com/preview/455/539987443c928fe664626d0f/desktop_mobile_1402924666026.jpg\"},{\"url\":\"http://d.c-launcher.com/preview/455/539987443c928fe664626d0f/drawer_mobile_1402924666348.jpg\"},{\"url\":\"http://d.c-launcher.com/preview/455/539987443c928fe664626d0f/widgets_mobile_1402924666659.jpg\"}]}]";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mFacade = new JsonFacade();
        mGson = new Gson();
    }
    
    public void onClick(View v) {
//        mFacade.fromJson("a test json string", new TypeToken<List<List<ThemeModel>>>(){}.getType());
        try {
			/*ThemeModel model = mFacade.fromJson(testJson, ThemeModel.class);
			Log.e(TAG, model.title + ":" + "id=" + model.id + ",previews=" + model.previews.size());*/
        	
        	List<ThemeModel> models = mFacade.fromJson(testJson, new TypeToken<List<ThemeModel>>(){}.getType());
        	Log.e(TAG, "model'size is " + models.size());
        	for(ThemeModel model : models) {
        		Log.e(TAG, model.title + ":has " + model.previews.size() + "previews");
        	}
        	
        	/*List<ThemeModel> models = mGson.fromJson(testJson, new TypeToken<List<ThemeModel>>(){}.getType());
        	Log.e(TAG, "model'size is " + models.size());
        	for(ThemeModel model : models) {
        		Log.e(TAG, model.title + ":has " + model.previews.size() + "previews");
        	}
        	
        	Log.e(TAG, models.toString());*/
        	
		} catch (Exception e) {
			
			e.printStackTrace();
		}
    	
    }
   
}
