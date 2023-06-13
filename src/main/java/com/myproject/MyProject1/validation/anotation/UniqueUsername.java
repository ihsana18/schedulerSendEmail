package com.myproject.MyProject1.validation.anotation;

import com.myproject.MyProject1.validation.validator.UniqueUsernameValid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = UniqueUsernameValid.class)
public @interface UniqueUsername {
    public Class<?>[] groups()default {};
    public Class<? extends Payload>[] payload()default {};
    public String message();
    public String username();
    public String currentUsername();

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface list{
        UniqueUsername[] value();
    }

}
