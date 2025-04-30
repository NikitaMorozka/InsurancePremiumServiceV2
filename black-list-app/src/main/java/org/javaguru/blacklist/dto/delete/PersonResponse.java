package org.javaguru.blacklist.dto.delete;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonResponse {
    private Long id;
    private String personFirstName;
    private String personLastName;
    private String personCode;
    private Boolean blacklisted;
}
