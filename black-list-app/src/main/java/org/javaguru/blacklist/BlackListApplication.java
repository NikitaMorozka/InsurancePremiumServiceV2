package org.javaguru.blacklist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BlackListApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlackListApplication.class, args);
    }

}
