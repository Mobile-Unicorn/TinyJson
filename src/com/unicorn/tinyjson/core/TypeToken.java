/**
 * 
 */
package com.unicorn.tinyjson.core;

import java.lang.reflect.Type;

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
	
	@Override
	public int hashCode() {
		return type.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		return (o instanceof TypeToken<?>) && TypeUtil.equal(type, ((TypeToken<?>) o).type);
	}
}
