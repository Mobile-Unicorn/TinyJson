/**
 * 
 */
package com.unicorn.tinyjson.internal;

import java.io.IOException;

import android.util.JsonReader;

/**
 * 类型适配器
 * <p>
 * 实现Json数据与Java对象的转换
 * </p>
 * @author xu
 *
 */
public interface TypeAdapter<T> {
	
	T read(JsonReader in) throws IOException;
}
