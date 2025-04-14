package org.javaguru.travel.insurance.core.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SimpleJob {
    private static final Logger logger = LoggerFactory.getLogger(SimpleJob.class);

    @Scheduled(fixedRate = 5000) // запуск каждые 5000 миллисекунд (5 секунд)
    public void executeJob() {
        logger.info("SimpleJob started");

        logger.info("SimpleJob finished");
    }
}
