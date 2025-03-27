package org.javaguru.travel.insurance.rest.v1.logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
class EventLoggerTravelCalculatePremiumRequest implements EventLogger {
    private static final Logger logger = LoggerFactory.getLogger(EventLoggerTravelCalculatePremiumRequest.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    EventLoggerTravelCalculatePremiumRequest() {
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void getLog(Object obj) {
        if (obj instanceof TravelCalculatePremiumRequestV1 request) {
            try {
                String json = objectMapper.writeValueAsString(request);
                logger.info("REQUEST: {}", json);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);

            }
        }
    }
}
