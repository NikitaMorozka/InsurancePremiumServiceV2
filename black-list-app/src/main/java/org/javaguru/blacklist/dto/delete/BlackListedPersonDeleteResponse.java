package org.javaguru.blacklist.dto.delete;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.javaguru.blacklist.dto.CoreResponse;
import org.javaguru.blacklist.dto.getAll.PersonResponse;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)

public class BlackListedPersonDeleteResponse extends CoreResponse {
    @JsonAlias("persons") private List<PersonResponse> persons;

}
