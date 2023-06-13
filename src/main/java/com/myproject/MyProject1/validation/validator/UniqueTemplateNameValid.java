package com.myproject.MyProject1.validation.validator;

import com.myproject.MyProject1.repository.RecipientRepository;
import com.myproject.MyProject1.service.abstraction.RecipientService;
import com.myproject.MyProject1.service.abstraction.TemplateMessageService;
import com.myproject.MyProject1.validation.anotation.UniqueTemplateName;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueTemplateNameValid implements ConstraintValidator<UniqueTemplateName,Object> {

    @Autowired
    private TemplateMessageService service;

    private String templateName;
    private String currentTemplateName;

    @Override
    public void initialize(UniqueTemplateName constraintAnnotation) {
        this.templateName=constraintAnnotation.templateName();
        this.currentTemplateName=constraintAnnotation.currentTemplateName();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        String valueTemplateName = new BeanWrapperImpl(o).getPropertyValue(templateName).toString();
        String valueCurrentTemplateName = new BeanWrapperImpl(o).getPropertyValue(currentTemplateName).toString();
        return !service.checkTemplate(valueTemplateName,valueCurrentTemplateName);
    }
}
