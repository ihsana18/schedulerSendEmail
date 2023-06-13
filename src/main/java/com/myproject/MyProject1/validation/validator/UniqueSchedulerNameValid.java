package com.myproject.MyProject1.validation.validator;

import com.myproject.MyProject1.service.abstraction.SchedulerService;
import com.myproject.MyProject1.validation.anotation.UniqueSchedulerName;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueSchedulerNameValid implements ConstraintValidator<UniqueSchedulerName,Object> {

    @Autowired
    private SchedulerService service;

    private String schedulerName;
    private String currentSchedulerName;

    @Override
    public void initialize(UniqueSchedulerName constraintAnnotation) {
        this.schedulerName=constraintAnnotation.schedulerName();
        this.currentSchedulerName= constraintAnnotation.currentSchedulerName();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        String valueSchedulerName = new BeanWrapperImpl(o).getPropertyValue(schedulerName).toString();
        String valueCurrentSchedName = new BeanWrapperImpl(o).getPropertyValue(currentSchedulerName).toString();
        return !service.checkSchedlerName(valueSchedulerName,valueCurrentSchedName);

    }
}
