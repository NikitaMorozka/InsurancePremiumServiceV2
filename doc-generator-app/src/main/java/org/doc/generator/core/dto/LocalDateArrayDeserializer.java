package org.doc.generator.core.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;

public class LocalDateArrayDeserializer extends JsonDeserializer<LocalDate> {

    @Override
    public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        int[] dateArray = p.readValueAs(int[].class);  // Читаем как массив
        return LocalDate.of(dateArray[0], dateArray[1], dateArray[2]);  // Преобразуем в LocalDate
    }
}