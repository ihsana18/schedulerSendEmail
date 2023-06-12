package com.myproject.MyProject1.validation.validator;

import com.myproject.MyProject1.service.abstraction.RecipientService;
import com.myproject.MyProject1.validation.anotation.UniqueRecipientEmail;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueRecipientEmailValid implements ConstraintValidator<UniqueRecipientEmail,String> {

    @Autowired
    private RecipientService service;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return !service.checkExistingName(email);
    }
}
