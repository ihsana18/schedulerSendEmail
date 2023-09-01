package com.myproject.MyProject1.validation.anotation;

import com.myproject.MyProject1.validation.validator.EmailValid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Constraint(validatedBy = EmailValid.class)
public @interface EmailValidation {
    public Class<?>[] groups()default {};
    public Class<? extends Payload>[] payload()default {};
    public String message();
}
