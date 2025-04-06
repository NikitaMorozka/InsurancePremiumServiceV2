package org.javaguru.travel.insurance.rest.getAgreement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.javaguru.travel.insurance.rest.logger.EventLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
class EventLoggerGetAgreementControllerResponse implements EventLogger {
    private static final Logger logger = LoggerFactory.getLogger(EventLoggerGetAgreementControllerResponse.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    EventLoggerGetAgreementControllerResponse() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void getLog(Object obj) {
        if (obj != null) {
            try {
                String json = objectMapper.writeValueAsString(obj);
                logger.info("RESPONSE: {}", json);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        } else {
            logger.info("Received null request");
        }
    }


}
