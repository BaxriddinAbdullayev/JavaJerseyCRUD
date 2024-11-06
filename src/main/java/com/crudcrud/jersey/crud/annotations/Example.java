package com.crudcrud.jersey.crud.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.METHOD,ElementType.TYPE})
public @interface Example {
    String value() default "";

}
