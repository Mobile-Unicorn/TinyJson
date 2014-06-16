/**
 * 
 */
package com.unicorn.tinyjson.utils;

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
	
}
