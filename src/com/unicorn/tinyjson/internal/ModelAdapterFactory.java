/**
 * 
 */
package com.unicorn.tinyjson.internal;

import java.io.Reader;

import android.util.JsonReader;

import com.unicorn.tinyjson.core.TypeToken;

/**
 * @author xu
 *
 */
public final class ModelAdapterFactory implements TypeAdapterFactory {

	@Override
	public <T> TypeAdapter<T> create(TypeToken<T> type) {
		
		return new ModelAdapter<T>();
	}

	/**
	 * 模型适配器
	 * <p>
	 * 实现{@link TypeAdapter}接口，用于实现Json数据与Model对象之间的转换
	 * </p>
	 * @author xu
	 *
	 * @param <T>
	 */
	public final class ModelAdapter<T> implements TypeAdapter<T> {

		@Override
		public T read(Reader reader) {
			JsonReader jReader = new JsonReader(reader);
			
			return null;
		}
		
	}
}
