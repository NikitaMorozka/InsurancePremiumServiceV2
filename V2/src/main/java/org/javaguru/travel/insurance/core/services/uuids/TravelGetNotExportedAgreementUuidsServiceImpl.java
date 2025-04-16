package org.javaguru.travel.insurance.core.services.uuids;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.command.TravelGetNotExportedAgreementUuidsCoreCommand;
import org.javaguru.travel.insurance.core.api.command.TravelGetNotExportedAgreementUuidsCoreResult;
import org.javaguru.travel.insurance.core.repositories.entities.AgreementRepository;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class TravelGetNotExportedAgreementUuidsServiceImpl implements TravelGetNotExportedAgreementUuidsService {

    private final AgreementRepository agreementRepository;

    @Override
    public TravelGetNotExportedAgreementUuidsCoreResult getResult(TravelGetNotExportedAgreementUuidsCoreCommand travelGetNotExportedAgreementUuidsCoreCommand) {
        List<UUID> uuids = convertBytesToUUID(agreementRepository.getNotExportedAgreementUuids());
        return new TravelGetNotExportedAgreementUuidsCoreResult(null, uuids);
    }

    private List<UUID> convertBytesToUUID(List<byte[]> bytes) {
        return bytes.stream().map(b -> {
            ByteBuffer bb = ByteBuffer.wrap(b);
            return new UUID(bb.getLong(), bb.getLong());
        }).toList();
    }

}
