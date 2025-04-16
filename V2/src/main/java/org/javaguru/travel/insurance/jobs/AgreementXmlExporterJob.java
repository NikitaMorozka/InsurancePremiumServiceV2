package org.javaguru.travel.insurance.jobs;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.command.TravelGetNotExportedAgreementUuidsCoreCommand;
import org.javaguru.travel.insurance.core.services.uuids.TravelGetNotExportedAgreementUuidsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class AgreementXmlExporterJob {
    private static final Logger logger = LoggerFactory.getLogger(AgreementXmlExporterJob.class);
    private final TravelGetNotExportedAgreementUuidsService travelGetNotExportedAgreementUuidsService;
    private final AgreementXmlExporter agreementXmlExporter;

    @Value("${agreement.xml.exporter.job.enabled}")
    private boolean agreementXmlExporterJobEnabled;

    @Value("${agreement.xml.exporter.job.thread-count}")
    private int threadCount;

    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.SECONDS)
    public void doJob() {
        if (agreementXmlExporterJobEnabled) {
            executeJob();
        }
    }

    void executeJob() {
        logger.info("AgreementXmlExporterJob started");
        List<UUID> uuids = travelGetNotExportedAgreementUuidsService.getResult(new TravelGetNotExportedAgreementUuidsCoreCommand())
                .getAgreementUuids();
        ExecutorService service = Executors.newFixedThreadPool(threadCount);
        Collection<Future<?>> futures = new LinkedList<>();
        uuids.forEach(uuid -> futures.add(service.submit(() -> agreementXmlExporter.exportAgreement(uuid))));
        waitUntilAllTasksWillBeExecuted(futures);
        service.shutdown();
        logger.info("AgreementXmlExporterJob finished");
    }

    private static void waitUntilAllTasksWillBeExecuted(Collection<Future<?>> futures) {
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException e) {
                logger.info("AgreementXmlExporterJob exception", e);
            } catch (ExecutionException e) {
                logger.info("AgreementXmlExporterJob exception", e);
            }
        }
    }


}