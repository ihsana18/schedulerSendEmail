package com.myproject.MyProject1.validation.validator;

import com.myproject.MyProject1.service.abstraction.RecipientService;
import com.myproject.MyProject1.validation.anotation.RecipientNameUpsert;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RecipientNameValid implements ConstraintValidator<RecipientNameUpsert,Object> {

    @Autowired
    private RecipientService service;

    private String name;
    private String currentName;

    @Override
    public void initialize(RecipientNameUpsert constraintAnnotation) {
        this.name = constraintAnnotation.name();
        this.currentName=constraintAnnotation.currentName();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        String valueName = new BeanWrapperImpl(o).getPropertyValue(name).toString();
        String valueCurrentName = new BeanWrapperImpl(o).getPropertyValue(currentName).toString();
        return !service.checkUpsert(valueName,valueCurrentName);
    }
}
