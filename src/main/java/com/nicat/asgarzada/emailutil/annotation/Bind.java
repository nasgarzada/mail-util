package com.nicat.asgarzada.emailutil.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation is for declaring key for field value.
 * If this annotation declared on field, then annotation processor {@link BindProcessor}
 * invokes getter of the field and puts value to the map
 *
 * @author nasgarzada
 * @version  1.0.0
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Bind {

    /**
     * @return key of value
     */
    String key();
}
