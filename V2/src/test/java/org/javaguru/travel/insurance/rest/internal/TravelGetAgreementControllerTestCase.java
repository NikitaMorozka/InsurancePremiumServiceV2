package org.javaguru.travel.insurance.rest.internal;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public abstract class TravelGetAgreementControllerTestCase {

//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired private JsonFileReader jsonFileReader;
//
////    private static final String BASE_URL = "/insurance/travel/api/internal/agreement/";
//
//
//    protected abstract String getTestCaseFolderName();
//
//    protected void executeAndCompare(UUID agreementUuid) throws Exception {
//        executeAndCompare(
//                agreementUuid,
//                getTestCaseFolderName() + "/request.json",
//                false
//        );
//    }
//
//    protected void executeAndCompare(UUID agreementUuid, boolean ignoreUUIDValue) throws Exception {
//        executeAndCompare(
//                agreementUuid,
//                getTestCaseFolderName() + "/request.json",
//                ignoreUUIDValue
//        );
//    }
//
//    protected void executeAndCompare(UUID agreementUuid,
//                                     String jsonResponseFilePath,
//                                     boolean ignoreUUIDValue) throws Exception {
//        MvcResult result = mockMvc.perform(get("/insurance/travel/api/internal/agreement/" + agreementUuid)
//                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        String responseBodyContent = result.getResponse().getContentAsString();
//
//        String jsonResponse = jsonFileReader.readJsonFromFile(jsonResponseFilePath);
//
//        if (ignoreUUIDValue) {
//
//            assertJson(responseBodyContent)
//                    .where()
//                    .keysInAnyOrder()
//                    .arrayInAnyOrder()
//                    .at("/uuid").isNotEmpty()
//                    .isEqualTo(jsonResponse);
//        } else {
//            assertJson(responseBodyContent)
//                    .where()
//                    .keysInAnyOrder()
//                    .arrayInAnyOrder()
//                    .isEqualTo(jsonResponse);
//        }
//    }

}
