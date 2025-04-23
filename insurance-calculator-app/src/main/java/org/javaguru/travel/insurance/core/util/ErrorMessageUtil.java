package org.javaguru.travel.insurance.core.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

@Component
public class ErrorMessageUtil {

    private final Properties props;

    ErrorMessageUtil() {
        try {
            props = PropertiesLoaderUtils.loadProperties(new ClassPathResource("errorCodes.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getErrorDescription(String errorCode) {
        return props.getProperty(errorCode);
    }

    public String getErrorDescription(String errorCode, List<Placeholder> placeholders) {
        String errorDescription = props.getProperty(errorCode);
        for(Placeholder placeholder : placeholders) {
            String placeholderToReplace = "{" + placeholder.getPlaceholderName() + "}";
            errorDescription = errorDescription.replace(placeholderToReplace, placeholder.getPlaceholderValue());
        }
        return errorDescription;
    }
}
