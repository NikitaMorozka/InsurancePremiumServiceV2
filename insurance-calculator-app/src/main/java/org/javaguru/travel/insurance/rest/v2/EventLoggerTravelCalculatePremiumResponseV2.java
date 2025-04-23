package org.javaguru.travel.insurance.rest.v2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.javaguru.travel.insurance.dto.v2.TravelCalculatePremiumResponseV2;
import org.javaguru.travel.insurance.rest.logger.EventLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
class EventLoggerTravelCalculatePremiumResponseV2 implements EventLogger {
    private static final Logger logger = LoggerFactory.getLogger(EventLoggerTravelCalculatePremiumResponseV2.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    EventLoggerTravelCalculatePremiumResponseV2() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void getLog(Object obj) {
        if (obj instanceof TravelCalculatePremiumResponseV2 responseV2) {
            try {
                String json = objectMapper.writeValueAsString(responseV2);
                logger.info("RESPONSE: {}", json);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        } else {
            logger.info("Received null request");
        }
    }
}
