/**
 * 
 */
package com.unicorn.tinyjson.internal;

import java.io.IOException;
import java.io.Reader;

import android.util.JsonReader;
import android.util.JsonWriter;
import android.util.Log;

import com.unicorn.tinyjson.core.TypeToken;

/**
 * @author xu
 *
 */
public final class ModelAdapterFactory implements TypeAdapterFactory {

    /** 构造器工厂实例 */
    private ConstructorFactory mConFactory;
    
    public ModelAdapterFactory() {
        mConFactory = new ConstructorFactory();
    }
    
	@Override
	public <T> TypeAdapter<T> create(TypeToken<T> type) {
		Class<? super T> raw = type.getRawType();
		
		if(!Object.class.isAssignableFrom(raw)) {     //不处理基本类型
		    return null;
		}
		
		return new ModelAdapter<T>(mConFactory.create(type));
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
	    
	    private String TAG = "ModelAdapterFactory.ModelAdapter";
	    
	    private final ObjectConstructor<T> mConstructor;
	    
	    public ModelAdapter(ObjectConstructor<T> constructor) {
	        mConstructor = constructor;
	        Log.e(TAG, "constructor is " + mConstructor.construct().getClass().getSimpleName());
	    }
	    
		@Override
		public T read(Reader reader) {
			JsonReader jReader = new JsonReader(reader);
			Log.e(TAG, "ModelAdapter will read " + reader.toString() + " as a java object");
			return null;
		}
		
	}
	
	/**
	 * 绑定域
	 * <p>
	 * 表示Java对象中与Json对象对应的字段，目前只提供反序列化子段
	 * </p>
	 * @author xuchunlei
	 *
	 */
	static abstract class BoundField {
	    final String name;
	    final boolean serialized;
	    final boolean deserialized;

	    protected BoundField(String name, boolean serialized, boolean deserialized) {
	      this.name = name;
	      this.serialized = serialized;
	      this.deserialized = deserialized;
	    }

	    abstract void write(JsonWriter writer, Object value) throws IOException, IllegalAccessException;
	    abstract void read(JsonReader reader, Object value) throws IOException, IllegalAccessException;
	  }
	
}
