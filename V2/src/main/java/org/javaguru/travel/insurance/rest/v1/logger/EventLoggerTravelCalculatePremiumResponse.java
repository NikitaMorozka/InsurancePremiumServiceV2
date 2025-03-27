package org.javaguru.travel.insurance.rest.v1.logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumResponseV1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
class EventLoggerTravelCalculatePremiumResponse implements EventLogger {
    private static final Logger logger = LoggerFactory.getLogger(EventLoggerTravelCalculatePremiumResponse.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    EventLoggerTravelCalculatePremiumResponse() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void getLog(Object obj) {
        if (obj instanceof TravelCalculatePremiumResponseV1 response) {
            try {
                String json = objectMapper.writeValueAsString(response);
                logger.info("RESPONSE: {}", json);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
