/**
 * 
 */
package com.unicorn.tinyjson.utils;

import android.util.Log;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 类型工具类
 * <p>
 * 提供类型的常用操作
 * </p>
 * @author xu
 *
 */
public final class TypeUtil {
    
    private static final String TAG = "TypeUtil";
    
	private TypeUtil() {
		
	}
	
	/**
	 * 判断两个类型是否等价
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean equal(Type a, Type b) {
		if(a == b) { //包括a = null,b = null的情况
			return true;
		} else if(a instanceof Class) {
			return a.equals(b);
		} 
		return false;
	}
	
	/**
	 * 将类型进行标准化
	 * <p>
	 * 处理一般类型、泛型参数类型、泛型通配符类型、泛型数组类型等
	 * </p>
	 * @param type
	 * @return
	 */
	public static Type canonicalize(Type type) {
	    if (type instanceof Class) {                       //普通类型
	        Class<?> c = (Class<?>) type;
	        return c.isArray() ? c.getComponentType() : c;
	    } else if(type instanceof ParameterizedType) {     //泛型类型
	        ParameterizedType p = (ParameterizedType) type;
	        Log.e(TAG, "ownerType is " + p.getOwnerType() + ",RawType is " + p.getRawType()
	                + ",actualTypeArguments is " + p.getActualTypeArguments()[0]);
	    } 
	    return null;
	}
	
}
