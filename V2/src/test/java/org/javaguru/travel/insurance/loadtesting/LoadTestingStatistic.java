package org.javaguru.travel.insurance.loadtesting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LoadTestingStatistic {
    private List<Long> executionTimes = new ArrayList<>();

    void addExecutionTimes(Long time) {
        executionTimes.add(time);
    }

     Boolean checkIsNullArray(){
        return executionTimes.isEmpty();
    }

    synchronized Long minimumValue() {
        return checkIsNullArray() ? 0L : Collections.min(executionTimes);
    }

    synchronized Long maxValue() {
        return checkIsNullArray() ? 0L : Collections.max(executionTimes);
    }

    synchronized Double averageValue () {
        return executionTimes.stream().mapToLong(Long::longValue).average().orElse(0);
    }
}


