package br.com.jpit.uteis.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CepValidator implements ConstraintValidator<ValidCep, String> {
    @Override
    public void initialize(ValidCep constraintAnnotation) {
    }

    @Override
    public boolean isValid(String cep, ConstraintValidatorContext constraintValidatorContext) {
        return cep != null && cep.matches("\\d{5}-\\d{3}");
    }
}
