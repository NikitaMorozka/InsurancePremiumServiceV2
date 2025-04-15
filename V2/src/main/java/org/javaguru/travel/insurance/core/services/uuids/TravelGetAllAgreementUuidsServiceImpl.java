package org.javaguru.travel.insurance.core.services.uuids;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.command.TravelGetAgreementUuidsCoreCommand;
import org.javaguru.travel.insurance.core.api.command.TravelGetAllAgreementUuidsCoreResult;
import org.javaguru.travel.insurance.core.repositories.entities.AgreementRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class TravelGetAllAgreementUuidsServiceImpl implements TravelGetAllAgreementUuidsService{

    private final AgreementRepository agreementRepository;

    @Override
    public TravelGetAllAgreementUuidsCoreResult getResult(TravelGetAgreementUuidsCoreCommand travelGetAgreementUuidsCoreCommand) {
        List<UUID> uuids = agreementRepository.findAllUuids();
        return new TravelGetAllAgreementUuidsCoreResult(null, uuids);
    }
}
