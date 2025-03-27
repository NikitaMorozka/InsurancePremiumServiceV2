package org.javaguru.travel.insurance.core.validations;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.util.ErrorMessageUtil;
import org.javaguru.travel.insurance.core.util.Placeholder;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Component
public class ErrorValidationFactory {

    private final ErrorMessageUtil errorCodeUtil;

    public ValidationErrorDTO processing(String error) {
        String err = errorCodeUtil.getErrorDescription(error);
        return new ValidationErrorDTO(error, err);
    }

    public ValidationErrorDTO processing(String errorCode, List<Placeholder> placeholders) {
        String errorDescription = errorCodeUtil.getErrorDescription(errorCode, placeholders);
        return new ValidationErrorDTO(errorCode, errorDescription);
    }
} 
