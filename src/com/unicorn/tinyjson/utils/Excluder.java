/**
 * 
 */
package com.unicorn.tinyjson.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

import com.unicorn.tinyjson.annotation.Expose;

/** 
 * 排除器
 * <p>
 * 用于排除不需要Json解析的字段
 * </p>
 * @author xu
 *
 */
public final class Excluder {
	//用于修饰符过滤
	private int modifiers = Modifier.TRANSIENT | Modifier.STATIC;
	
	/**
	 * 
	 * @param field
	 * @return
	 */
	public boolean excludeField(Field field) {
		
		if ((modifiers & field.getModifiers()) != 0) {	//排除静态类型和不可序列化的字段
			return true;
		}
		
		Expose annotation = field.getAnnotation(Expose.class);
		if(annotation == null) {	//排除没有Expose注解的字段
			return true;
		}
		return false;
	}
}
