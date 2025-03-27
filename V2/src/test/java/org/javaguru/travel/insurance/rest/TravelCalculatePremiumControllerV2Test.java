package org.javaguru.travel.insurance.rest;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.util.Objects;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class) // запуск спринг теста

class TravelCalculatePremiumControllerV2Test {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JsonFileReader jsonFileReader;

    @ParameterizedTest
    @MethodSource({"jsonTestCases"})


    void controllerTest(String testCase) throws Exception {

        String request = String.format("src/test/resources/restV2/%s/request.json", testCase);
        String response = String.format("src/test/resources/restV2/%s/response.json", testCase);

        mockMvc.perform(post("/insurance/travel/api/v2/")
                        .content(jsonFileReader.readJsonFromFile(request))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonFileReader.readJsonFromFile(response)))
                .andReturn();
    }

    static Stream<Arguments> jsonTestCases() {
        File dir = new File("src/test/resources/restV2");
        return Stream.of(Objects.requireNonNull(dir.listFiles(File::isDirectory)))
                .map(File::getName)
                .sorted()
                .map(Arguments::of);
    }


}
