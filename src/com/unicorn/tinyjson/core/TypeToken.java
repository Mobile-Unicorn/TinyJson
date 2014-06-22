/**
 * 
 */
package com.unicorn.tinyjson.core;

import android.util.Log;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.unicorn.tinyjson.utils.TypeUtil;

/**
 * 类型标记类
 * <p>
 * Java未提供表示泛型类型的方式。该类封装了类型，不仅可以表示基本类型，也可以用于表示泛型的类型。
 * </p>
 * @author xu
 *
 */
public class TypeToken<T> {
    
    private static final String TAG = "TypeToken"; 
    
    /** 声明类型，当T为泛型类型时，表示声明类型，如List<String>,则rawType为List */
    final Class<? super T> rawType;
    /** 参数类型 ，当T为泛型类型时，表明参数类型，如List<String>,则type为String*/
	final Type type;
	
	final int hashCode;
	
	/**
	 * 限制通过继承的方式创建新的实例
	 * <p>
	 * 使用这种方式可以在运行期获得实例的类型，一般用于解析泛型类型
	 * </p>
	 */
	@SuppressWarnings("unchecked")
    protected TypeToken() {
		type = getSuperclassTypeParameter(getClass());
		rawType = (Class<? super T>)TypeUtil.getRawType(type);
		hashCode = type.hashCode();
	}
	
	/**
	 * 获得父类的类型参数
	 * @param subClass
	 * @return
	 */
	Type getSuperclassTypeParameter(Class<?> subClass) {
	    Type superclass = getClass().getGenericSuperclass();
        if (superclass instanceof Class) {
          throw new RuntimeException("Missing type parameter.");
        }
        
        ParameterizedType p = (ParameterizedType) superclass;
        return TypeUtil.canonicalize(p.getActualTypeArguments()[0]);
	}
	 
	/**
	 * 构造方法
	 * <p>
	 * 用于解析普通类型
	 * </p>
	 * @param type
	 */
	@SuppressWarnings("unchecked")
    TypeToken(Type type) {
	    this.type = TypeUtil.canonicalize(type);
	    this.rawType = (Class<? super T>)TypeUtil.getRawType(this.type);
	    this.hashCode = this.type.hashCode();
	}
	
	/**
	 * 获得基本类型
	 * @return
	 */
	public Type getType() {
	    return type;
	}
	
	/**
	 * 获得声明的类型
	 * @return
	 */
	public Class<? super T> getRawType() {
	    return rawType;
	}
	
    /**
     * 获得给定Class类型的类型标记实例
     */
    /*public static <T> TypeToken<T> get(Class<T> type) {
        return new TypeToken<T>(type);
    }*/
	
    /**
     * 获得给定{@link Type}类型的类型标记实例
     */
    public static TypeToken<?> get(Type type) {
      return new TypeToken<Object>(type);
    }
    
	@Override
	public int hashCode() {
		return hashCode;
	}
	
	@Override
	public boolean equals(Object o) {
		return (o instanceof TypeToken<?>) && TypeUtil.equals(type, ((TypeToken<?>) o).type);
	}
}
