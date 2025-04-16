package org.javaguru.travel.insurance.core.api.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TravelGetNotExportedAgreementUuidsCoreResult {

    private List<ValidationErrorDTO> errors;

    private List<UUID> agreementUuids;

    public boolean hasErrors() {
        return errors != null && !errors.isEmpty();
    }

    public TravelGetNotExportedAgreementUuidsCoreResult(List<ValidationErrorDTO> errors) {
        this.errors = errors;
    }

}
