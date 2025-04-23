package org.javaguru.travel.insurance.core.services.loader;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.command.TravelGetAgreementCoreCommand;
import org.javaguru.travel.insurance.core.api.command.TravelGetAgreementCoreResult;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.services.TravelGetAgreementService;
import org.javaguru.travel.insurance.core.validations.TravelAgreementUuidValidator;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class TravelGetAgreementServiceImpl implements TravelGetAgreementService {

    private final TravelAgreementUuidValidator agreementUuidValidator;
    private final AgreementLoader agreementDTOLoader;

    @Override
    public TravelGetAgreementCoreResult getAgreement(TravelGetAgreementCoreCommand travelGetAgreementCoreCommand) {
        List<ValidationErrorDTO> errors = agreementUuidValidator.validate(travelGetAgreementCoreCommand.getUuid());
        return errors.isEmpty()
                ? buildResponse(travelGetAgreementCoreCommand.getUuid())
                : buildResponse(errors);
    }

    private TravelGetAgreementCoreResult buildResponse(List<ValidationErrorDTO> errors) {
        return new TravelGetAgreementCoreResult(errors);
    }

    private TravelGetAgreementCoreResult buildResponse(UUID uuid) {
        TravelGetAgreementCoreResult travelGetAgreementCoreResult = new TravelGetAgreementCoreResult();
        travelGetAgreementCoreResult.setAgreement(agreementDTOLoader.load(uuid));
        return travelGetAgreementCoreResult;
    }


}
