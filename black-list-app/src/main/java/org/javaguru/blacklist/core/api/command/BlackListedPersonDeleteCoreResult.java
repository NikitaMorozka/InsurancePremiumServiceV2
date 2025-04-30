package org.javaguru.blacklist.core.api.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.javaguru.blacklist.core.api.dto.BlackListedPersonDTO;
import org.javaguru.blacklist.core.api.dto.ValidationErrorDTO;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlackListedPersonDeleteCoreResult {

    private List<ValidationErrorDTO> errors;

    private List<BlackListedPersonDTO> blackListedPersons;

    public BlackListedPersonDeleteCoreResult(List<BlackListedPersonDTO> blackListedPersons) {
        this.blackListedPersons = blackListedPersons;
    }
}