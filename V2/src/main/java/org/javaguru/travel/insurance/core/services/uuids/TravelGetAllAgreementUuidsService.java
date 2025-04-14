package org.javaguru.travel.insurance.core.services.uuids;

import org.javaguru.travel.insurance.core.api.command.TravelGetAgreementUuidsCoreCommand;
import org.javaguru.travel.insurance.core.api.command.TravelGetAllAgreementUuidsCoreResult;

public interface TravelGetAllAgreementUuidsService {
    TravelGetAllAgreementUuidsCoreResult getResult(TravelGetAgreementUuidsCoreCommand travelGetAgreementUuidsCoreCommand);
}
