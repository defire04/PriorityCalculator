package com.example.util;

import com.example.model.MathExample;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.stereotype.Component;

@Component
public class MathExampleValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return MathExample.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MathExample mathExample = (MathExample) target;

        int openingBraceCounter = 0;
        int closingBraceCounter = 0;
        for (String symb : mathExample.getExample().split("")) {
            if(symb.equals("(")){
                openingBraceCounter++;
            } else if (symb.equals(")")) {
                closingBraceCounter++;
            }
        }
        if(openingBraceCounter != closingBraceCounter){
            errors.rejectValue("example", "", "Возможно, не хватает открывающих или закрывающих скобок!");
        }
    }
}
