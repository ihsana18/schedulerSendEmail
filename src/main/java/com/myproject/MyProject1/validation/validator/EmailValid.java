package com.myproject.MyProject1.validation.validator;

import com.myproject.MyProject1.validation.anotation.EmailValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValid implements ConstraintValidator<EmailValidation,String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        String regex = "^[A-Za-z0-9+_.-]+@gmail\\.com$";
        String regex2 = "^[A-Za-z0-9+_.-]+@yahoo\\.com$";
        String regex3 = "^[A-Za-z0-9+_.-]+@yahoo\\.co\\.id$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);

        Pattern pattern2 = Pattern.compile(regex2);
        Matcher matcher2 = pattern2.matcher(s);

        Pattern pattern3 = Pattern.compile(regex3);
        Matcher matcher3 = pattern3.matcher(s);

        if(matcher.matches()||matcher2.matches()||matcher3.matches()){
            return true;
        }else{
            return false;
        }
    }
}
