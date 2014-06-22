/*
 * Copyright (C) 2014 The TinyJson Project of Unicorn
 *
 */

package com.unicorn.tinyjson.internal;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;

import android.util.JsonReader;
import android.util.JsonToken;

import com.unicorn.tinyjson.core.JsonContext;
import com.unicorn.tinyjson.core.TypeToken;
import com.unicorn.tinyjson.utils.TypeUtil;

/**
 * 集合类数据适配工厂
 * <p>
 * 目前支持{@link java.util.List List}类型的数据
 * </p>
 */
public final class CollectionTypeAdapterFactory implements TypeAdapterFactory {

  /** 构造器工厂实例 */
  private final ConstructorFactory mConFactory;
  
  private JsonContext mContext;
  
  public CollectionTypeAdapterFactory(JsonContext context) {
	  mConFactory = ConstructorFactory.getInstance();
	  mContext = context;
  }

  public <T> TypeAdapter<T> create(TypeToken<T> typeToken) {
    Type type = typeToken.getType();

    Class<? super T> rawType = typeToken.getRawType();
    if (!Collection.class.isAssignableFrom(rawType)) {	//不处理非Collection类型的对象
      return null;
    }

    Type elementType = TypeUtil.getCollectionElementType(type, rawType);
    TypeAdapter<?> elementTypeAdapter = mContext.getAdapter(TypeToken.get(elementType));
    ObjectConstructor<T> constructor = mConFactory.create(typeToken);

    @SuppressWarnings({"unchecked", "rawtypes"}) // create() doesn't define a type parameter
    TypeAdapter<T> result = new Adapter(constructor, elementTypeAdapter);
    return result;
  }

  private final class Adapter<E> implements TypeAdapter<Collection<E>> {

	  private final ObjectConstructor<? extends Collection<E>> mConstructor;
	  private TypeAdapter<E> mElementAdapter;
	  
	  public Adapter(ObjectConstructor<? extends Collection<E>> constructor, TypeAdapter<E> elementAdapter) {
		  mConstructor = constructor;
		  mElementAdapter = elementAdapter;
	  }
	  
	@Override
	public Collection<E> read(JsonReader in) throws IOException {
		if (in.peek() == JsonToken.NULL) {
	        in.nextNull();
	        return null;
	      }
		
	      Collection<E> collection = mConstructor.construct();
	      in.beginArray();
	      while (in.hasNext()) {
	        E instance = mElementAdapter.read(in);
	        collection.add(instance);
	      }
	      in.endArray();
	      return collection;
	}
	  
  }
}
