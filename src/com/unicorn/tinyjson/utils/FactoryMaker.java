/*
 * Copyright (C) 2014 The TinyJson Project of Unicorn
 *
 */
package com.unicorn.tinyjson.utils;

import java.io.IOException;

import android.util.JsonReader;
import android.util.JsonToken;

import com.unicorn.tinyjson.core.TypeToken;
import com.unicorn.tinyjson.internal.JsonParseException;
import com.unicorn.tinyjson.internal.TypeAdapter;
import com.unicorn.tinyjson.internal.TypeAdapterFactory;

/**
 * @author xu
 *
 */
public final class FactoryMaker {
	
	private FactoryMaker() {
		
	}
	
	/** 整型数据适配器 */
	private static final TypeAdapter<Number> INTEGER = new TypeAdapter<Number>() {
	    @Override
	    public Number read(JsonReader in) throws IOException {
	      if (in.peek() == JsonToken.NULL) {
	        in.nextNull();
	        return null;
	      }
	      try {
	        return in.nextInt();
	      } catch (NumberFormatException e) {
	        throw new JsonParseException(e);
	      }
	    }
	  };
	
	/** 字符串数据适配器 */
	public static final TypeAdapter<String> STRING = new TypeAdapter<String>() {
		@Override
		public String read(JsonReader in) throws IOException {
			JsonToken peek = in.peek();
			if (peek == JsonToken.NULL) {
				in.nextNull();
				return null;
			}
			
			return in.nextString();
		}
	};
	  
	/** 整型数据适配器工厂 */
	public static final TypeAdapterFactory INTEGER_FACTORY = newFactory(
			int.class, Integer.class, INTEGER);
	
	/** 字符串数据适配器工厂 */
	public static final TypeAdapterFactory STRING_FACTORY = newFactory(
			String.class, STRING);
	  
	/**
	 * 创建适配器工厂
	 * @param unboxed 拆箱类型
	 * @param boxed	装箱类型
	 * @param typeAdapter
	 * @return
	 */
	private static <F> TypeAdapterFactory newFactory(
			final Class<F> unboxed, final Class<F> boxed, final TypeAdapter<? super F> typeAdapter) {
		return new TypeAdapterFactory() {
		      @SuppressWarnings("unchecked") 
		      public <T> TypeAdapter<T> create(TypeToken<T> typeToken) {
		        Class<? super T> rawType = typeToken.getRawType();
		        return (rawType == unboxed || rawType == boxed) ? (TypeAdapter<T>) typeAdapter : null;
		      }
		      @Override public String toString() {
		        return "Factory[type=" + boxed.getName()
		            + "+" + unboxed.getName() + ",adapter=" + typeAdapter + "]";
		      }
		    };
	}
	
	/**
	 * 创建适配器工厂
	 * @param type
	 * @param typeAdapter
	 * @return
	 */
	public static <TT> TypeAdapterFactory newFactory(
		      final Class<TT> type, final TypeAdapter<TT> typeAdapter) {
		    return new TypeAdapterFactory() {
		      @SuppressWarnings("unchecked") 
		      public <T> TypeAdapter<T> create(TypeToken<T> typeToken) {
		        return typeToken.getRawType() == type ? (TypeAdapter<T>) typeAdapter : null;
		      }
		      @Override public String toString() {
		        return "Factory[type=" + type.getName() + ",adapter=" + typeAdapter + "]";
		      }
		    };
		  }
}
