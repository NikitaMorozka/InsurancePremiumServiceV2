package org.javaguru.travel.insurance.core.services.uuids;

import org.javaguru.travel.insurance.core.api.command.TravelGetNotExportedAgreementUuidsCoreCommand;
import org.javaguru.travel.insurance.core.api.command.TravelGetNotExportedAgreementUuidsCoreResult;

public interface TravelGetNotExportedAgreementUuidsService {
    TravelGetNotExportedAgreementUuidsCoreResult getResult(TravelGetNotExportedAgreementUuidsCoreCommand travelGetNotExportedAgreementUuidsCoreCommand);
}
