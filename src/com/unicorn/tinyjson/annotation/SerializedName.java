/*
 * Copyright (C) 2008 Google Inc.
 *
 */

package com.unicorn.tinyjson.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于指明成员变量是否可以与Json数据之间进行序列化/反序列化操作
 * 
 * <p>注意: 为成员变量指明的value值必须是合法的Json子段名称.</p>
 *
 * @author xuchunlei
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SerializedName {

  /**
   * @return 成员变量对应的Json字段名称
   */
  String value();
}
