package com.iovereye.sdk.internal.mapping;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 数据结构属性注解
 * 
 * @author JieMo
 * @since 1.0, 06 03, 2014
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.FIELD })
public @interface ApiField {

	/** JSON属性映射名称 **/
	public String value() default "";
	
}
