package org.javaguru.travel.insurance.rest.internal;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TravelGetAgreementControllerTestCase1 extends TravelGetAgreementControllerTestCase {

//    @Autowired private MockMvc mockMvc;
//    @Autowired private JsonFileReader jsonFileReader;
//
//    @Test
//    public void shouldGetAgreement() throws Exception {
//        UUID uuid = calculateAgreementAndGetUuid();
//        executeAndCompare(uuid, true);
//    }
//
//    private UUID calculateAgreementAndGetUuid() throws Exception {
//        String jsonRequest = jsonFileReader.readJsonFromFile("rest/restV2/request.json");
//
//        MvcResult result = mockMvc.perform(post("/insurance/travel/api/v2/")
//                        .content(jsonRequest)
//                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        String responseBodyContent = result.getResponse().getContentAsString();
//
////        TravelCalculatePremiumResponseV2 response = new ObjectMapper().readValue(responseBodyContent, TravelCalculatePremiumResponseV2.class);
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.registerModule(new JavaTimeModule());
//        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//
//        TravelCalculatePremiumResponseV2 response =
//                mapper.readValue(responseBodyContent, TravelCalculatePremiumResponseV2.class);
//
//        return response.getUuid();
//    }
//
//    @Override
//    protected String getTestCaseFolderName() {
//        return "rest/restV2";
//    }

}
