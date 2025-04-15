package org.javaguru.travel.insurance.jobs;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.command.TravelGetAgreementCoreCommand;
import org.javaguru.travel.insurance.core.api.command.TravelGetAgreementUuidsCoreCommand;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.services.TravelGetAgreementService;
import org.javaguru.travel.insurance.core.services.uuids.TravelGetAllAgreementUuidsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class AgreementXmlExporterJob {
    private static final Logger logger = LoggerFactory.getLogger(AgreementXmlExporterJob.class);
    private final TravelGetAgreementService travelGetAgreementService;
    private final TravelGetAllAgreementUuidsService travelGetAllAgreementUuidsService;

    @Value( "${agreement.xml.exporter.job.path}" )
    private String agreementExportPath;

    @Value("${agreement.xml.exporter.job.enabled}")
    private boolean agreementXmlExporterJobEnabled;

    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.SECONDS)
    public void doJob() {
        if (agreementXmlExporterJobEnabled) {
            executeJob();
        }
    }

    void executeJob() {
            logger.info("AgreementXmlExporterJob started");
            List<UUID> uuids = travelGetAllAgreementUuidsService.getResult(new TravelGetAgreementUuidsCoreCommand())
                    .getAgreementUuids();

        uuids.forEach(this::exportAgreement);

        logger.info("AgreementXmlExporterJob finished");
    }

    private void exportAgreement(UUID agreementUuid) {
        try {
            logger.info("AgreementXmlExporterJob started for uuid = " + agreementUuid);
            AgreementDTO agreement = getAgreementData(agreementUuid);
            String agreementXml = convertAgreementToXml(agreement);
            storeXmlToFile(agreementUuid, agreementXml);
            logger.info("AgreementXmlExporterJob finished for uuid = " + agreementUuid);
        } catch (Exception e) {
            logger.info("AgreementXmlExporterJob failed for agreement uuid = " + agreementUuid, e);
        }
    }

    private AgreementDTO getAgreementData(UUID agreementUuid) {
        TravelGetAgreementCoreCommand command = new TravelGetAgreementCoreCommand(agreementUuid);
        return travelGetAgreementService.getAgreement(command).getAgreement();
    }

    private String convertAgreementToXml(AgreementDTO agreement) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(AgreementDTO.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        StringWriter sw = new StringWriter();
        marshaller.marshal(agreement,sw);
        return sw.toString();
    }

    private void storeXmlToFile(UUID agreementUuid, String agreementXml) throws IOException {
        Path path = Paths.get(agreementExportPath, "agreement" + agreementUuid + ".xml");
        Files.writeString(path, agreementXml,StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }
}