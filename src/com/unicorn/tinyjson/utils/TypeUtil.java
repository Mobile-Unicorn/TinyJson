/**
 * 
 */
package com.unicorn.tinyjson.utils;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * 类型工具类
 * <p>
 * 提供类型的常用操作
 * </p>
 * @author xu
 *
 */
public final class TypeUtil {
    
    private static final String TAG = "TypeUtil";
    
	private TypeUtil() {
		
	}
	
	/**
	 * 判断两个类型是否等价
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean equals(Type a, Type b) {
        if(a == b) { // 包括a = null,b = null的情况
            return true;
        } else if (a instanceof Class) {
            return a.equals(b);
        } else if (a instanceof ParameterizedType) {
            
            if (!(b instanceof ParameterizedType)) {
                return false;
            }
            
            ParameterizedType pa = (ParameterizedType) a;
            ParameterizedType pb = (ParameterizedType) b;
            return equal(pa.getOwnerType(), pb.getOwnerType())
                    && pa.getRawType().equals(pb.getRawType())
                    && Arrays.equals(pa.getActualTypeArguments(), pb.getActualTypeArguments());
        }
        return false;
	}
	
	/**
	 * 解析类型，将可支持的类型进行标准化
	 * <p>
	 * 处理一般类型、泛型参数类型、泛型通配符类型、泛型数组类型等
	 * </p>
	 * @param type
	 * @return
	 */
	public static Type canonicalize(Type type) {
	    if (type instanceof Class) {                       //普通类型
	        Class<?> c = (Class<?>) type;
	        if(c.isArray()) {                              //不处理数组类型
	        	throw new UnsupportedOperationException("Couldn't handle Array Type");
	        }
	        return c;
	    } else if(type instanceof ParameterizedType) {     //参数化类型（处理泛型类的情况）
	        ParameterizedType p = (ParameterizedType) type;
	        return new ParameterizedTypeImpl(p.getOwnerType(),
	                p.getRawType(), p.getActualTypeArguments());
	    } else {                                           //不处理其他类型
			throw new UnsupportedOperationException("Couldn't handle Type[" + type.getClass().getSimpleName() +"]" );
		}
	}
	/**
	 * 获得声明类型
	 * @param type
	 * @return
	 */
    public static Class<?> getRawType(Type type) {
        if (type instanceof Class<?>) { // 普通类型
            return (Class<?>) type;
        } else if (type instanceof ParameterizedType) { // 参数化类型（处理泛型类的情况）
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type rawType = parameterizedType.getRawType();
            return (Class<?>) rawType;
        } else { // 不处理其他情况
            String className = type == null ? "null" : type.getClass().getName();
            throw new IllegalArgumentException("Expected a Class, ParameterizedType, but <" + type
                    + "> is of type " + className);
        }
    }
	
	/**
	 * 可以在框架中使用的参数类型
	 * <p>
	 * 通过canonicalize(Type)对上层类型（ownerType）、声明类型（rawType）和类型参数（typeArgument）进行了递归赋值
	 * 可用于表达复杂的嵌套类型
	 * </p>
	 * @author xuchunlei
	 *
	 */
    private static final class ParameterizedTypeImpl implements ParameterizedType, Serializable {
        private final Type ownerType;
        private final Type rawType;
        private final Type[] typeArguments;

        public ParameterizedTypeImpl(Type ownerType, Type rawType, Type... typeArguments) {
            this.ownerType = ownerType == null ? null : canonicalize(ownerType);
            this.rawType = canonicalize(rawType);
            this.typeArguments = typeArguments.clone();
            for (int t = 0; t < this.typeArguments.length; t++) {
                this.typeArguments[t] = canonicalize(this.typeArguments[t]);
            }
        }

        public Type[] getActualTypeArguments() {
            return typeArguments.clone();
        }

        public Type getRawType() {
            return rawType;
        }

        public Type getOwnerType() {
            return ownerType;
        }

        @Override
        public boolean equals(Object other) {
            return other instanceof ParameterizedType
                    && TypeUtil.equals(this, (ParameterizedType) other);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(typeArguments)
                    ^ rawType.hashCode()
                    ^ hashCodeOrZero(ownerType);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(30 * (typeArguments.length + 1));
            stringBuilder.append(typeToString(rawType));

            if (typeArguments.length == 0) {
                return stringBuilder.toString();
            }

            stringBuilder.append("<").append(typeToString(typeArguments[0]));
            for (int i = 1; i < typeArguments.length; i++) {
                stringBuilder.append(", ").append(typeToString(typeArguments[i]));
            }
            return stringBuilder.append(">").toString();
        }

        private static final long serialVersionUID = 0;
    }
	
    /**
     * 类型的字符串信息
     * <p>
     * Type为Class时，toString方法会打印出Class字样，为统一格式，需要加以区分。
     * </p> 
     */
	public static String typeToString(Type type) {
	    return type instanceof Class ? ((Class<?>) type).getName() : type.toString();
	}
	/**
	 * 处理对象可能为空时的哈希值
	 * @param o
	 * @return
	 */
	private static int hashCodeOrZero(Object o) {
	    return o != null ? o.hashCode() : 0;
	}
	/**
	 * 处理对象可能为空时的等价
	 * @param a
	 * @param b
	 * @return
	 */
	private static boolean equal(Object a, Object b) {
        return a == b || (a != null && a.equals(b));
    }
}
