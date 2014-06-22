/**
 * 
 */
package com.unicorn.tinyjson.internal;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

import com.unicorn.tinyjson.annotation.SerializedName;
import com.unicorn.tinyjson.core.JsonContext;
import com.unicorn.tinyjson.core.TypeToken;
import com.unicorn.tinyjson.utils.Excluder;
import com.unicorn.tinyjson.utils.TypeUtil;

/**
 * @author xu
 *
 */
public final class ModelAdapterFactory implements TypeAdapterFactory {

    /** 构造器工厂实例 */
    private final ConstructorFactory mConFactory;
    
    private JsonContext mContext;
    
    public ModelAdapterFactory(JsonContext context) {
        mConFactory = ConstructorFactory.getInstance();
        mContext = context;
    }
    
	@Override
	public <T> TypeAdapter<T> create(TypeToken<T> type) {
		Class<? super T> raw = type.getRawType();
		
		if(!Object.class.isAssignableFrom(raw)) {     //不处理基本类型
		    return null;
		}
		
		return new ModelAdapter<T>(mConFactory.create(type), getBoundFields(type));
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
	    private final Map<String, BoundField> mBoundFields;
	    
	    public ModelAdapter(ObjectConstructor<T> constructor, Map<String, BoundField> boundFields) {
	        mConstructor = constructor;
	        mBoundFields = boundFields;
	    }
	    
		@SuppressWarnings("resource")
		@Override
		public T read(JsonReader in) throws IOException {
			if (in.peek() == JsonToken.NULL) { // 如果为空，则消费掉空对象
				in.nextNull();
				return null;
			}

			T instance = mConstructor.construct();

			try {
				in.beginObject();
				while (in.hasNext()) {
					String name = in.nextName();
					BoundField field = mBoundFields.get(name);
					if (field == null) {
						in.skipValue();
					} else {
						field.read(in, instance);
					}
				}
			} catch (IllegalStateException e) {
				throw new JsonParseException(e);
			} catch (IllegalAccessException e) {
				throw new AssertionError(e);
			}
			in.endObject();
			return instance;
		}
		
	}
	
	/**
	 * 绑定字段
	 * <p>
	 * 表示Java对象中与Json对象对应的字段，目前只提供反序列化子段
	 * </p>
	 * @author xuchunlei
	 *
	 */
	static abstract class BoundField {
		/** Json对象字段名 */
	    final String name;

	    protected BoundField(String name) {
	      this.name = name;
	    }
	    
		static BoundField createBoundField(final JsonContext context,
				final Field field, final TypeToken<?> fieldType) {
			SerializedName serializedName = field
					.getAnnotation(SerializedName.class);
			String name = serializedName == null ? field.getName()
					: serializedName.value();
			return new BoundField(name) {

				@Override
				void read(JsonReader reader, Object value) throws IOException,
						IllegalAccessException {
					Log.e("ModelAdapter", "type:" + fieldType.getType()
							+ ",rawType:" + fieldType.getRawType());
					TypeAdapter<?> adapter = context.getAdapter(fieldType);
					Object fieldValue = adapter.read(reader);
					if (fieldValue != null) {
						field.set(value, fieldValue);
					}
				}
			};

		}
	    
	    
	    /**
	     * 从Json对象中读取字段数据并赋值给Java对象对应字段
	     * @param reader
	     * @param value
	     * @throws IOException
	     * @throws IllegalAccessException
	     */
	    abstract void read(JsonReader reader, Object value) throws IOException, IllegalAccessException;
	  }
	
	/**
	 * 获得绑定字段集合
	 * @return
	 */
	private Map<String, BoundField> getBoundFields(TypeToken<?> type) {
		Excluder excluder = new Excluder();
		Map<String, BoundField> result = new LinkedHashMap<String, BoundField>();
		Class<?> rawType = type.getRawType();
		if(rawType.isInterface()) {
			return result;
		}
		Type declaredType = type.getType();
		while(rawType != Object.class) {
			Field[] fields = rawType.getDeclaredFields();
			for(Field field : fields) {
				if(excluder.excludeField(field)) {
					continue;
				}
				field.setAccessible(true);
				Type fieldType = TypeUtil.resolve(type.getType(), rawType, field.getGenericType());
				BoundField boundField = BoundField.createBoundField(mContext, field, TypeToken.get(fieldType));
				BoundField previous = result.put(boundField.name, boundField);
				if (previous != null) {
			          throw new IllegalArgumentException(declaredType
			              + " declares multiple JSON fields named " + previous.name);
			        }
			}
			type = TypeToken.get(TypeUtil.resolve(type.getType(), rawType, rawType.getGenericSuperclass()));
		    rawType = type.getRawType();
		}
		return result;
	}
	

}
