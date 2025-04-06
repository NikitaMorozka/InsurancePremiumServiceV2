package org.javaguru.travel.insurance.rest.v2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.javaguru.travel.insurance.dto.v2.TravelCalculatePremiumRequestV2;
import org.javaguru.travel.insurance.rest.logger.EventLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
class EventLoggerTravelCalculatePremiumRequestV2 implements EventLogger {
    private static final Logger logger = LoggerFactory.getLogger(EventLoggerTravelCalculatePremiumRequestV2.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    EventLoggerTravelCalculatePremiumRequestV2() {
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void getLog(Object obj) {
        if (obj instanceof TravelCalculatePremiumRequestV2 requestV2) {
            try {
                String json = objectMapper.writeValueAsString(requestV2);
                logger.info("REQUEST: {}", json);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);

            }
        }
    }
}
