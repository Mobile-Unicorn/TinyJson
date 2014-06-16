/**
 * 
 */
package com.unicorn.tinyjson.internal;

import java.io.Reader;

/**
 * 类型适配器
 * <p>
 * 实现Json数据与Java对象的转换
 * </p>
 * @author xu
 *
 */
public interface TypeAdapter<T> {
	
	T read(Reader reader);
}
