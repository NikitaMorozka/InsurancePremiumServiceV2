package org.doc.generator.core.messagebroker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.doc.generator.core.dto.AgreementDTO;
import org.springframework.stereotype.Component;

@Component
class JsonStringToAgreementDtoConverter {

    public AgreementDTO convert(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, AgreementDTO.class);
    }

}
