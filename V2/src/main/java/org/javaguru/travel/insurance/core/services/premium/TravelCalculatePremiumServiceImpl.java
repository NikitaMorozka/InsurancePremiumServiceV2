package org.javaguru.travel.insurance.core.services.premium;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.command.TravelCalculatePremiumCoreCommand;
import org.javaguru.travel.insurance.core.api.command.TravelCalculatePremiumCoreResult;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.domain.entities.Agreement;
import org.javaguru.travel.insurance.core.services.TravelCalculatePremiumService;
import org.javaguru.travel.insurance.core.validations.TravelAgreementValidator;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class TravelCalculatePremiumServiceImpl implements TravelCalculatePremiumService {

    private final TravelAgreementValidator agreementValidator;
    private final CalculateRisksForPremium calculateRisksForPremium;
    private final CalculateTotalPremiumAgreement totalPremiumAgreement;
    private final AgreementSaverService agreementSaverService;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public TravelCalculatePremiumCoreResult calculatePremium(TravelCalculatePremiumCoreCommand command) {
        List<ValidationErrorDTO> errors = agreementValidator.validate(command.getAgreement());
        if (errors.isEmpty()) {
            calculatePremium(command.getAgreement());
            Agreement agreement =  agreementSaverService.save(command.getAgreement());
            command.getAgreement().setUuid(agreement.getUuid());
            return buildResponse(command.getAgreement());
        }
        return new TravelCalculatePremiumCoreResult(errors);
    }

    private void calculatePremium(AgreementDTO agreement) {
        calculateRisksForPremium.calculateRiskPremiumsForAllPersons(agreement);
        agreement.setAgreementPremium(totalPremiumAgreement.calculateTotalAgreementPremium(agreement));
    }

    private TravelCalculatePremiumCoreResult buildResponse(AgreementDTO agreement) {
        return new TravelCalculatePremiumCoreResult(null, agreement);
    }

}


