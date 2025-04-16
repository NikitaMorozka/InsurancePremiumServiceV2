package org.javaguru.travel.insurance.core.services.xml;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.command.TravelExportAgreementToXmlCoreCommand;
import org.javaguru.travel.insurance.core.api.command.TravelExportAgreementToXmlCoreResult;
import org.javaguru.travel.insurance.core.api.command.TravelGetAgreementCoreCommand;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.domain.entities.AgreementXmlExport;
import org.javaguru.travel.insurance.core.repositories.entities.AgreementXmlExportRepository;
import org.javaguru.travel.insurance.core.services.TravelGetAgreementService;
import org.javaguru.travel.insurance.core.validations.ErrorValidationFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class TravelExportAgreementToXmlServiceImpl implements TravelExportAgreementToXmlService{
    private final TravelGetAgreementService travelGetAgreementService;
    private static final Logger logger = LoggerFactory.getLogger(TravelExportAgreementToXmlServiceImpl.class);
    private final AgreementXmlExportRepository agreementXmlExportRepository;
    private final ErrorValidationFactory errorsHandler;

    @Value("${agreement.xml.exporter.job.path}")
    private String agreementExportPath;

    public TravelExportAgreementToXmlCoreResult export(TravelExportAgreementToXmlCoreCommand command){
        try {
            AgreementDTO agreement = getAgreementData( command.getUuid());
            String agreementXml = convertAgreementToXml(agreement);
            storeXmlToFile( command.getUuid(), agreementXml);
        } catch (Exception e) {
            logger.info("AgreementXmlExporterJob failed for agreement uuid %s".formatted(command.getUuid()), e);
            return new TravelExportAgreementToXmlCoreResult(List.of(
                    errorsHandler.processing("ERROR_CODE_20")));
        }
        saveToDatabaseInfoAboutExportedAgreement(command);

        return new TravelExportAgreementToXmlCoreResult();
    }

    private void saveToDatabaseInfoAboutExportedAgreement(TravelExportAgreementToXmlCoreCommand command) {
        AgreementXmlExport agreementXmlExportEntity = new AgreementXmlExport();
        agreementXmlExportEntity.setAgreementUuid(command.getUuid());
        agreementXmlExportEntity.setAlreadyExported(Boolean.TRUE);
        agreementXmlExportRepository.save(agreementXmlExportEntity);
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
        marshaller.marshal(agreement, sw);
        return sw.toString();
    }

    private void storeXmlToFile(UUID agreementUuid, String agreementXml) throws IOException {
        Path path = Paths.get(agreementExportPath, "agreement" + agreementUuid + ".xml");
        Files.writeString(path, agreementXml, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }


}
