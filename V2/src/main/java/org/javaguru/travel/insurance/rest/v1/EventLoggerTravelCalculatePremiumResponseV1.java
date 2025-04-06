package org.javaguru.travel.insurance.rest.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumResponseV1;
import org.javaguru.travel.insurance.rest.logger.EventLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
class EventLoggerTravelCalculatePremiumResponseV1 implements EventLogger {
    private static final Logger logger = LoggerFactory.getLogger(EventLoggerTravelCalculatePremiumResponseV1.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    EventLoggerTravelCalculatePremiumResponseV1() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void getLog(Object obj) {
        if (obj instanceof TravelCalculatePremiumResponseV1 responseV1) {
            try {
                String json = objectMapper.writeValueAsString(responseV1);
                logger.info("RESPONSE: {}", json);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        } else {
            logger.info("Received null request");
        }
    }
}
