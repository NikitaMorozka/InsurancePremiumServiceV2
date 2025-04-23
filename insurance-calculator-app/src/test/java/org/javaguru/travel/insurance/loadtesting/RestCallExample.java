package org.javaguru.travel.insurance.loadtesting;

import java.util.ArrayList;
import java.util.List;

public class RestCallExample {

    public static void main(String[] args) {
        List<Thread> threads = new ArrayList<>();

        LoadTestingStatistic statisticV1 = new LoadTestingStatistic();
        LoadTestingStatistic statisticV2 = new LoadTestingStatistic();

        for (int i = 0; i < 50; i++) {
            var thread1 = new Thread(new ExecuteV1Call(statisticV1));
            var thread2 = new Thread(new ExecuteV2Call(statisticV2));
            thread1.start();
            thread2.start();
            threads.add(thread1);
            threads.add(thread2);
        }

        threads.forEach(thread -> {
            try {
                thread.join(); // заставляет поток main() метода дождаться завершения работы потока
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        System.out.printf("statisticV1 ср. время: %.2f ms\n", statisticV1.averageValue());
        System.out.println("statisticV1 мин. время: " + statisticV1.minimumValue() + " ms");
        System.out.println("statisticV1 макс. время: " + statisticV1.maxValue() + " ms\n");
        System.out.printf("statisticV2 ср. время: %.2f ms\n", statisticV2.averageValue());
        System.out.println("statisticV2 мин. время: " + statisticV2.minimumValue() + " ms");
        System.out.println("statisticV2 макс. время: " + statisticV2.maxValue() + " ms\n");


    }
}
