package com.myproject.MyProject1.validation.validator;

import com.myproject.MyProject1.validation.anotation.Compare;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CompareValid implements ConstraintValidator<Compare,Object> {
    private String firstField;
    private String secondField;

    @Override
    public void initialize(Compare constraintAnnotation) {
        this.firstField=constraintAnnotation.firstField();
        this.secondField=constraintAnnotation.secondField();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        String firstValue = new BeanWrapperImpl(o).getPropertyValue(firstField).toString();
        String secondValue=new BeanWrapperImpl(o).getPropertyValue(secondField).toString();
        return (firstValue.equals(secondValue));
    }
}
