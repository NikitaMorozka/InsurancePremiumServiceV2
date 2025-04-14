package org.javaguru.travel.insurance.loadtesting;

import com.google.common.base.Stopwatch;
import org.javaguru.travel.insurance.rest.JsonFileReader;

public class ExecuteV2Call extends ExecuteRestCallAndCompareResults implements Runnable {

    private static final String CALCULATE_PREMIUM_V1_LOCAL_URL = "http://localhost:8080/insurance/travel/api/v2/";
    private JsonFileReader jsonFileReader = new JsonFileReader();
    private LoadTestingStatistic loadTestingStatistic;

    ExecuteV2Call(LoadTestingStatistic loadTestingStatistic) {
        this.loadTestingStatistic = loadTestingStatistic;
    }

    public void run() {
        String jsonRequest = jsonFileReader.readJsonFromFile("rest/restV2/request.json");
        String jsonResponse = jsonFileReader.readJsonFromFile("rest/restV2/response.json");
        Stopwatch stopwatch = Stopwatch.createStarted();
        executeRestCallAndCompareResults(jsonRequest, jsonResponse, CALCULATE_PREMIUM_V1_LOCAL_URL);
        stopwatch.stop();
        long times = stopwatch.elapsed().toMillis();
        loadTestingStatistic.addExecutionTimes(times);
    }
}
