/**
 * 
 */
package com.unicorn.tinyjson.utils;


import java.io.Serializable;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;

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
    
    static final Type[] EMPTY_TYPE_ARRAY = new Type[] {};
    
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
		} else if (a instanceof WildcardType) {
			if (!(b instanceof WildcardType)) {
				return false;
			}

			WildcardType wa = (WildcardType) a;
			WildcardType wb = (WildcardType) b;
			return Arrays.equals(wa.getUpperBounds(), wb.getUpperBounds())
					&& Arrays.equals(wa.getLowerBounds(), wb.getLowerBounds());

		}
        return false;
	}
	
	/**
	 * 解析类型，将可支持的类型进行规范化
	 * <p>
	 * 处理一般类型、泛型参数类型
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
//			throw new UnsupportedOperationException("Couldn't handle Type[" + type.getClass().getSimpleName() +"]" );
	    	return type;
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
        } else if (type instanceof TypeVariable) { 		//类型变量（处理泛型参数类型的情况）
            return Object.class;
        } else {										// 不处理其他情况
        	String className = type == null ? "null" : type.getClass().getName();
            throw new IllegalArgumentException("Expected a Class, ParameterizedType, TypeVariable, but <" + type
                    + "> is of type " + className);
		}
    }
	/**
	 * 递归解析可以创建的类型
	 * <p>
	 * 可以避免创建一些中间对象
	 * </p>
	 * @param context
	 * @param contextRawType
	 * @param toResolve
	 * @return
	 */
	public static Type resolve(Type context, Class<?> contextRawType,
			Type toResolve) {
		while (true) {
			if (toResolve instanceof ParameterizedType) {
				ParameterizedType original = (ParameterizedType) toResolve;
				Type ownerType = original.getOwnerType();
				Type newOwnerType = resolve(context, contextRawType, ownerType);
				boolean changed = newOwnerType != ownerType;

				Type[] args = original.getActualTypeArguments();
				for (int t = 0, length = args.length; t < length; t++) {
					Type resolvedTypeArgument = resolve(context,
							contextRawType, args[t]);
					if (resolvedTypeArgument != args[t]) {
						if (!changed) {
							args = args.clone();
							changed = true;
						}
						args[t] = resolvedTypeArgument;
					}
				}

				return changed ? newParameterizedTypeWithOwner(newOwnerType,
						original.getRawType(), args) : original;

			} else if (toResolve instanceof TypeVariable) {
				TypeVariable<?> typeVariable = (TypeVariable<?>) toResolve;
				toResolve = resolveTypeVariable(context, contextRawType,
						typeVariable);
				if (toResolve == typeVariable) {
					return toResolve;
				}

			} else {
				return toResolve;
			}
		}
	}
    
	/**
	 * 获取集合元素类型
	 */
	public static Type getCollectionElementType(Type context,
			Class<?> contextRawType) {
		Type collectionType = getSupertype(context, contextRawType,
				Collection.class);

		if (collectionType instanceof WildcardType) {
			collectionType = ((WildcardType) collectionType).getUpperBounds()[0];
		}
		if (collectionType instanceof ParameterizedType) {
			return ((ParameterizedType) collectionType)
					.getActualTypeArguments()[0];
		}
		return Object.class;
	}
	
	/**
	 * 返回 {@code supertype}的泛型格式. 例如{@code ArrayList<String>}将返回 {@code Iterable<String>} 的 {@code Iterable.class}格式.
	 * 
	 * @param 父类或接口.
	 */
	private static Type getSupertype(Type context, Class<?> contextRawType,
			Class<?> supertype) {

		if (!supertype.isAssignableFrom(contextRawType)) {
			throw new IllegalArgumentException();
		}
		return resolve(context, contextRawType, getGenericSupertype(context, contextRawType, supertype));
	}

	/**
	 * 返回 {@code supertype}的超类类型. 例如类 {@code IntegerSet}，超类为{@code Set.class} 时返回 {@code Set<Integer>}，
	 * 超类为{@code Collection.class}时返回{@code Collection<Integer>}.
	 */
	private static Type getGenericSupertype(Type context, Class<?> rawType,
			Class<?> toResolve) {
		if (toResolve == rawType) {
			return context;
		}

		// we skip searching through interfaces if unknown is an interface
		if (toResolve.isInterface()) {
			Class<?>[] interfaces = rawType.getInterfaces();
			for (int i = 0, length = interfaces.length; i < length; i++) {
				if (interfaces[i] == toResolve) {
					return rawType.getGenericInterfaces()[i];
				} else if (toResolve.isAssignableFrom(interfaces[i])) {
					return getGenericSupertype(
							rawType.getGenericInterfaces()[i], interfaces[i],
							toResolve);
				}
			}
		}

		// check our supertypes
		if (!rawType.isInterface()) {
			while (rawType != Object.class) {
				Class<?> rawSupertype = rawType.getSuperclass();
				if (rawSupertype == toResolve) {
					return rawType.getGenericSuperclass();
				} else if (toResolve.isAssignableFrom(rawSupertype)) {
					return getGenericSupertype(rawType.getGenericSuperclass(),
							rawSupertype, toResolve);
				}
				rawType = rawSupertype;
			}
		}

		return toResolve;
	}
	  
	/**
	 * 返回参数化类型.
	 * 
	 * @return a {@link java.io.Serializable serializable} parameterized type.
	 */
	private static ParameterizedType newParameterizedTypeWithOwner(
			Type ownerType, Type rawType, Type... typeArguments) {
		return new ParameterizedTypeImpl(ownerType, rawType, typeArguments);
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
	
	static Type resolveTypeVariable(Type context, Class<?> contextRawType,
			TypeVariable<?> unknown) {
		Class<?> declaredByRaw = declaringClassOf(unknown);

		// we can't reduce this further
		if (declaredByRaw == null) {
			return unknown;
		}

		Type declaredBy = getGenericSupertype(context, contextRawType,
				declaredByRaw);
		if (declaredBy instanceof ParameterizedType) {
			int index = indexOf(declaredByRaw.getTypeParameters(), unknown);
			return ((ParameterizedType) declaredBy).getActualTypeArguments()[index];
		}

		return unknown;
	}
	
	private static int indexOf(Object[] array, Object toFind) {
	    for (int i = 0; i < array.length; i++) {
	      if (toFind.equals(array[i])) {
	        return i;
	      }
	    }
	    throw new NoSuchElementException();
	  }
	
	/**
	   * Returns the declaring class of {@code typeVariable}, or {@code null} if it was not declared by
	   * a class.
	   */
	  private static Class<?> declaringClassOf(TypeVariable<?> typeVariable) {
	    GenericDeclaration genericDeclaration = typeVariable.getGenericDeclaration();
	    return genericDeclaration instanceof Class
	        ? (Class<?>) genericDeclaration
	        : null;
	  }
	
    
    /**
     * 类型的字符串信息
     * <p>
     * Type为Class时，toString方法会打印出Class字样，为统一格式，需要加以区分。
     * </p> 
     */
	private static String typeToString(Type type) {
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
