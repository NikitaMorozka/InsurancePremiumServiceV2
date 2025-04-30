package org.javaguru.blacklist.core.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlackListedPersonDTO {

    private Long id;

    private String personFirstName;

    private String personLastName;

    private String personCode;

    private Boolean blackListed;

}
