package com.myproject.MyProject1.validation.validator;

import com.myproject.MyProject1.repository.RecipientRepository;
import com.myproject.MyProject1.service.abstraction.RecipientService;
import com.myproject.MyProject1.service.abstraction.TemplateMessageService;
import com.myproject.MyProject1.validation.anotation.UniqueTemplateName;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueTemplateNameValid implements ConstraintValidator<UniqueTemplateName,String> {

    @Autowired
    private TemplateMessageService service;


    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        return !service.checkTemplate(name);
    }
}
