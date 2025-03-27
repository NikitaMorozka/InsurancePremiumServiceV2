package org.javaguru.travel.insurance.rest.v1.logger;


import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
class EventLoggerTravelCalculatePremiumRequestExecutionTime implements EventLogger {
    private static final Logger logger = LoggerFactory.getLogger(EventLoggerTravelCalculatePremiumRequestExecutionTime.class);

    @Override
    public void getLog(Object obj) {
        if (obj instanceof Stopwatch stopwatch) {
            long time = stopwatch.stop().elapsed().toMillis();
            logger.info("Request processing time (ms): {}", time);
        }
    }
}
