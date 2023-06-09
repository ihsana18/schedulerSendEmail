package com.myproject.MyProject1.validation.anotation;

import com.myproject.MyProject1.validation.validator.UniqueRecipientEmailValid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueRecipientEmailValid.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueRecipientEmail {
    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default {};
    public String message();


}
