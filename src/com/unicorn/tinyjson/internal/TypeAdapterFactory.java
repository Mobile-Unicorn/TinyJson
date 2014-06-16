/**
 * 
 */
package com.unicorn.tinyjson.internal;

/**
 * @author xu
 *
 */
public interface TypeAdapterFactory {
	
	<T> TypeAdapter<T> create();
}
