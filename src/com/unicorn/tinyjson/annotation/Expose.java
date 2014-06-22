/*
 * Copyright (C) 2014 The TinyJson Project of Unicorn
 * 
 */

package com.unicorn.tinyjson.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表示成员变量可以被Json反序列化的注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Expose {
  
  /**
   * 如果设置为{@code true}，标记此注解的字段可以从Json被反序列化生成.否则将被忽略.
   * 默认为 {@code true}.
   */
  public boolean deserialize() default true;
}
