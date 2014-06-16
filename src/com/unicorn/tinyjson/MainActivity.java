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

import com.unicorn.tinyjson.core.JsonFacade;

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
        
       /* int num = 2000000;
        
        Sieve s = new Sieve();
        s.computeNor(num);
        
        s.computeQui(num);*/
        
//        Log.e(TAG, "class is " + );
        
        
      /*  RequestQueue queue = Volley.newRequestQueue(this);
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
        
        queue.add(request);*/
        
        
        
    }
  
    
   /* public class Sieve {  
           
    	private void computeNor(int num) {
            int n = 20000000;  
            long start = System.currentTimeMillis();  
            BitSet b = new BitSet(n+1);  
            int count = 0;  
            int i;  
            for(i = 2; i <= n;i++){  
                b.set(i);  
            }  
            i = 2;  
            while(i*i <=n){  
                if(b.get(i)){  
                    count++;  
                    int k = 2 * i;  
                    while(k <= n){  
                        b.clear(k);  
                        k +=i;  
                    }  
                }  
                i++;  
            }  
            while(i <= n){  
                if(b.get(i)){  
                    count++;  
                }  
                i++;  
            }  
            long end = System.currentTimeMillis();  
            System.out.println("count = " + count);  
            System.out.println((end-start) +" milliseconds"); 
            String s;
            s.ch
    	}
    	
    	private void computeQui(int num) {
    		// TODO Auto-generated method stub  
            int n = 20000000;  
            long start = System.currentTimeMillis();  
            int count = 0;  
            boolean is = false;  
            for(int i = 2;i <= n;i++){  
                for(int j = 2; j <= Math.sqrt(i);j++){  
                    if( i % j == 0){  
                        is = true;  
                        break;  
                    }  
                }  
                if(!is){  
                    count++;  
                }  
                is = false;  
            }  
            long end = System.currentTimeMillis();  
            System.out.println("count = "+count);  
            System.out.println((end-start)+" milliseconds"); 
    	}
    	
    }*/
}
