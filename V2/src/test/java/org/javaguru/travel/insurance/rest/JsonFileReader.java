package org.javaguru.travel.insurance.rest;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class JsonFileReader {
    public String readJsonFromFile(String filePath) {
        try{
            return new String(Files.readAllBytes(Path.of(filePath)));
        }catch (IOException e ){
            throw new RuntimeException(e);
        }
    }
}
