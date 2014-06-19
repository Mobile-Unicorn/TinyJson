/**
 * 
 */
package com.unicorn.tinyjson.core;

import android.util.Log;

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
    
	final Type type;
	final int hashCode;
	
	/**
	 * 限制通过继承的方式创建新的实例
	 * <p>
	 * 使用这种方式可以在运行期获得实例的类型
	 * </p>
	 */
	protected TypeToken() {
		type = getSuperclassTypeParameter(getClass());
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
        Log.e(TAG, "ownerType is " + p.getOwnerType());
        return TypeUtil.canonicalize(p.getActualTypeArguments()[0]);
	}
	 
	/**
	 * 构造方法
	 * <p>
	 * 用于
	 * </p>
	 * @param type
	 */
	TypeToken(Type type) {
	    this.type = TypeUtil.canonicalize(type);
	    this.hashCode = this.type.hashCode();
	}
	
	/**
	 * 获得基本类型实例
	 * @return
	 */
	public Type getType() {
	    return type;
	}
	
    /**
     * 获得给定Class类型的类型标记实例
     */
//    public static <T> TypeToken<T> get(Class<T> type) {
//        return new TypeToken<T>(type);
//    }
	
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
		return (o instanceof TypeToken<?>) && TypeUtil.equal(type, ((TypeToken<?>) o).type);
	}
}
