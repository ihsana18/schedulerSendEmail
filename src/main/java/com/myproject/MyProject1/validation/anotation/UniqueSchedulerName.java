package com.myproject.MyProject1.validation.anotation;

import com.myproject.MyProject1.validation.validator.RecipientNameValid;
import com.myproject.MyProject1.validation.validator.UniqueSchedulerNameValid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = UniqueSchedulerNameValid.class)
public @interface UniqueSchedulerName {
    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default {};
    public String message();

    public String schedulerName();
    public String currentSchedulerName();

    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        UniqueSchedulerName[] value();
    }
}
