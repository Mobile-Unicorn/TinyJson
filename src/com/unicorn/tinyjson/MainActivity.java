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
import com.unicorn.tinyjson.ThemeModel.Preview;
import com.unicorn.tinyjson.core.JsonFacade;
import com.unicorn.tinyjson.core.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
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
        ThemeModel model1 = new ThemeModel();
        model1.id = 1;
        model1.title = "one";
        model1.previews = new ArrayList<Preview>();
        model1.previews.add(new Preview("url-1"));
        model1.previews.add(new Preview("url-2"));
        model1.previews.add(new Preview("url-3"));
        
        ThemeModel model2 = new ThemeModel();
        model2.id = 2;
        model2.title = "two";
        model2.previews = new ArrayList<Preview>();
        model2.previews.add(new Preview("url-1"));
        model2.previews.add(new Preview("url-2"));
        model2.previews.add(new Preview("url-3"));
        
        List<ThemeModel> data = new ArrayList<ThemeModel>();
        data.add(model1);
        data.add(model2);
        
        Gson g = new Gson();
        String result = g.toJson(data);
        Log.e(TAG, result);
        
       /* List<ThemeModel> restore = g.fromJson(result, new TypeToken<List<ThemeModel>>(){}.getType());
        for(ThemeModel model : restore) {
        	Log.e(TAG, "name is " + model.title);
        }*/
        
		try {
			List<ThemeModel> restore = mFacade.fromJson(testJson, new TypeToken<List<ThemeModel>>(){}.getType());
			for(ThemeModel model : restore) {
	        	Log.e(TAG, "name is " + model.title);
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
   
    /* try {
			ThemeModel model = mFacade.fromJson(testJson, ThemeModel.class);
			Log.e(TAG, model.title + ":" + "id=" + model.id + ",previews=" + model.previews.size());
        	
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
        	
        	Log.e(TAG, models.toString());
        	
		} catch (Exception e) {
			
			e.printStackTrace();
		}
    */
}
