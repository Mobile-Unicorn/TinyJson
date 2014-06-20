/*
 * Copyright (C) 2014 The TinyJson Project of Unicorn
 *
 */
package com.unicorn.tinyjson.internal;

/**
 * 泛型对象构造器工厂
 * <p>
 * 用于构造对象实例，使用该类可以在将Json字符串解析为对象时，创建类实例期间进行对象导航（处理嵌套的对象）
 * </p>
 * @author xuchunlei
 *
 */
public interface ObjectConstructor<T> {

    public T construct();
}
