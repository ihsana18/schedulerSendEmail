package com.myproject.MyProject1.validation.validator;

import com.myproject.MyProject1.service.abstraction.UserService;
import com.myproject.MyProject1.validation.anotation.UniqueUsername;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUsernameValid implements ConstraintValidator<UniqueUsername,Object> {
    @Autowired
    private UserService service;

    private String username;
    private String currentUsername;

    @Override
    public void initialize(UniqueUsername constraintAnnotation) {

        this.username= constraintAnnotation.username();
        this.currentUsername= constraintAnnotation.currentUsername();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        String value=new BeanWrapperImpl(o).getPropertyValue(username).toString();
        String valueCurrent=new BeanWrapperImpl(o).getPropertyValue(currentUsername).toString();
        return !service.checkUsername(value,valueCurrent);
    }
}
