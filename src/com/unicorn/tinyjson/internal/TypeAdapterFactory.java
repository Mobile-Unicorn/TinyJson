/**
 * 
 */
package com.unicorn.tinyjson.internal;

import com.unicorn.tinyjson.core.TypeToken;

/**
 * 类型适配器工厂接口
 * <p>
 * 实现此接口的对象可以创建{@link TypeAdapter}对象
 * </p>
 * @author xu
 *
 */
public interface TypeAdapterFactory {
	
    /**
     * 创建类型适配器
     * @param   type 类型
     * @return  {@link TypeAdapter}适配器实例
     */
	<T> TypeAdapter<T> create(TypeToken<T> type);
}
