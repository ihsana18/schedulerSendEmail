package com.myproject.MyProject1.validation.anotation;

import com.myproject.MyProject1.validation.validator.UniqueTemplateNameValid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueTemplateNameValid.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueTemplateName {
    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default {};
    public String message();

    public String currentTemplateName();
    public String templateName();

    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        UniqueTemplateName[] value();
    }

}
