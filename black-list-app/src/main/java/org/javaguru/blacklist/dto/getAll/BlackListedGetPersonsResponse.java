package org.javaguru.blacklist.dto.getAll;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.javaguru.blacklist.dto.CoreResponse;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlackListedGetPersonsResponse extends CoreResponse {
    @JsonAlias("persons") private List<PersonResponse> persons;
}
