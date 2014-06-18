/**
 * 
 */
package com.unicorn.tinyjson.core;

import java.lang.reflect.Type;

import com.google.gson.reflect.TypeToken;
import com.unicorn.tinyjson.utils.TypeUtil;

/**
 * 类型标记类
 * <p>
 * 用于表示泛型类型
 * </p>
 * @author xu
 *
 */
public final class TypeToken<T> {
	final Type type;
	
	protected TypeToken() {
		type = null;
	}
	
	TypeToken(Type type) {
	    this.type = TypeUtil.canonicalize(type);
	}
	
    /**
     * 获得给定Class类型的类型标记实例
     */
    public static <T> TypeToken<T> get(Class<T> type) {
        return new TypeToken<T>(type);
    }
	
    /**
     * 获得给定{@link Type}类型的类型标记实例
     */
    public static TypeToken<?> get(Type type) {
      return new TypeToken<Object>(type);
    }
    
	@Override
	public int hashCode() {
		return type.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		return (o instanceof TypeToken<?>) && TypeUtil.equal(type, ((TypeToken<?>) o).type);
	}
}
