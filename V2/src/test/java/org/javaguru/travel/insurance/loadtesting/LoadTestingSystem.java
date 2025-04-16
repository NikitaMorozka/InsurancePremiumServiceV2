package org.javaguru.travel.insurance.loadtesting;

import java.util.ArrayList;
import java.util.List;

class LoadTestingSystem {

    public static void main(String[] args) {
        new LoadTestingSystem().executeForAMinute(1, 20);
    }

    public void executeForAMinute(int parallelThreadCount, int requestCount) {
        long intervalBetweenRequestsInMillis = 30000L / requestCount;

        LoadTestingStatistic statisticV1 = new LoadTestingStatistic();

        List<Thread> threads = new ArrayList<>();

        for (int i = 1; i <= requestCount; i++) {
            for (int j = 1; j <= parallelThreadCount; j++) {
                Thread v1Call = new Thread(new ExecuteV1Call(statisticV1));
                v1Call.start();
                threads.add(v1Call);
            }
            try {
                Thread.sleep(intervalBetweenRequestsInMillis);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        System.out.println("V1 average = " + statisticV1.averageValue());
        System.out.println("V1 min = " + statisticV1.minimumValue());
        System.out.println("V1 max = " + statisticV1.maxValue());
    }

}
