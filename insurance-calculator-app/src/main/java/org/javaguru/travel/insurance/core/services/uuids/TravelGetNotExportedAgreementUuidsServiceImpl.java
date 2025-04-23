package org.javaguru.travel.insurance.core.services.uuids;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.command.TravelGetNotExportedAgreementUuidsCoreCommand;
import org.javaguru.travel.insurance.core.api.command.TravelGetNotExportedAgreementUuidsCoreResult;
import org.javaguru.travel.insurance.core.repositories.entities.AgreementRepository;
import org.javaguru.travel.insurance.core.util.ConvertBytesToUuidUtil;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class TravelGetNotExportedAgreementUuidsServiceImpl implements TravelGetNotExportedAgreementUuidsService {
    private final ConvertBytesToUuidUtil convertBytesToUUID;
    private final AgreementRepository agreementRepository;

    @Override
    public TravelGetNotExportedAgreementUuidsCoreResult getResult(TravelGetNotExportedAgreementUuidsCoreCommand travelGetNotExportedAgreementUuidsCoreCommand) {
        List<UUID> uuids = convertBytesToUUID.convertBytesToUUID(agreementRepository.getNotExportedAgreementUuids());
        return new TravelGetNotExportedAgreementUuidsCoreResult(null, uuids);
    }


}
