package pl.training.shop.commons.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Lock {

    String value() default "default";

    LockType type() default LockType.WRITE;

    enum LockType {

        READ, WRITE

    }

}
