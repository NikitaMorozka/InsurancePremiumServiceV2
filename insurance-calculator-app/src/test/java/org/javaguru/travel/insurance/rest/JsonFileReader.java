package org.javaguru.travel.insurance.rest;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class JsonFileReader {
    public String readJsonFromFile(String filePath) {
        try{
            var resource = new ClassPathResource(filePath);
            Path path = resource.getFile().toPath(); // преобразуем в Path
            return Files.readString(path);
        }catch (IOException e ){
            throw new RuntimeException(e);
        }
    }
}
