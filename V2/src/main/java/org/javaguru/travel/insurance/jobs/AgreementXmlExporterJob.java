package org.javaguru.travel.insurance.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AgreementXmlExporterJob {
    private static final Logger logger = LoggerFactory.getLogger(AgreementXmlExporterJob.class);

    @Value("${agreement.xml.exporter.job.enabled}")
    private boolean agreementXmlExporterJobEnabled;

    @Scheduled(fixedRate = 5000)

    public void executeJob() {
        if(agreementXmlExporterJobEnabled) {
            logger.info("SimpleJob started");
            logger.info("SimpleJob finished");
        }
    }
}
