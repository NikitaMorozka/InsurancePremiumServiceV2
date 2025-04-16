package org.javaguru.travel.insurance.jobs;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.command.TravelExportAgreementToXmlCoreCommand;
import org.javaguru.travel.insurance.core.services.xml.TravelExportAgreementToXmlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class AgreementXmlExporter {

    private static final Logger logger = LoggerFactory.getLogger(AgreementXmlExporterJob.class);

    private final TravelExportAgreementToXmlService agreementToXmlService;

    public void exportAgreement(UUID agreementUuid) {
        logger.info("AgreementXmlExporterJob started for uuid = " + agreementUuid);
        agreementToXmlService.export(new TravelExportAgreementToXmlCoreCommand(agreementUuid));
        logger.info("AgreementXmlExporterJob finished for uuid = " + agreementUuid);
    }

}