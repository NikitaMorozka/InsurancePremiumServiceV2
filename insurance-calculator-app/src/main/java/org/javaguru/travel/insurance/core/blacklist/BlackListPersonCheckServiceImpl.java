package org.javaguru.travel.insurance.core.blacklist;

import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.blacklist.dto.BlackListedPersonCheckRequest;
import org.javaguru.travel.insurance.core.blacklist.dto.BlackListedPersonCheckResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Profile({"mysql-container", "mysql-local"})
class BlackListPersonCheckServiceImpl implements BlackListPersonCheckService {

    private static final Logger logger = LoggerFactory.getLogger(BlackListPersonCheckServiceImpl.class);

    private final String personBlacklistedCheckUrl;

    private final RestTemplate restTemplate;

    BlackListPersonCheckServiceImpl(@Value("${person.blacklisted.check.url}")
                                    String personBlacklistedCheckUrl,
                                    RestTemplate restTemplate) {
        this.personBlacklistedCheckUrl = personBlacklistedCheckUrl;
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean isPersonBlacklisted(PersonDTO personDTO) {
        logger.info("Blacklisted check for person with code " + personDTO.getPersonCode() + " started!");

        // Set the request headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        BlackListedPersonCheckRequest request = new BlackListedPersonCheckRequest();
        request.setPersonFirstName(personDTO.getPersonFirstName());
        request.setPersonLastName(personDTO.getPersonLastName());
        request.setPersonCode(personDTO.getPersonCode());

        // Create an HttpEntity object with the request body and headers
        HttpEntity<BlackListedPersonCheckRequest> requestEntity = new HttpEntity<>(request, headers);

        // Make the POST request and expect a BlackListedPersonCheckResponse object in response
        BlackListedPersonCheckResponse response = restTemplate.postForObject(personBlacklistedCheckUrl, request, BlackListedPersonCheckResponse.class);

        logger.info("Blacklisted check for person with code " + personDTO.getPersonCode() + " return " + response.getBlacklisted());

        return response.getBlacklisted();
    }

}
