package org.rafeleduardo.congestiontaxcalculator.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LocalDateTimeDeserializer extends JsonDeserializer<List<LocalDateTime>> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public List<LocalDateTime> deserialize(JsonParser p, DeserializationContext ctxt) throws JsonProcessingException, IOException {
        JsonNode node = p.getCodec().readTree(p);
        List<LocalDateTime> dates = new ArrayList<>();

        for (JsonNode dateNode : node) {
            String date = dateNode.asText();
            dates.add(LocalDateTime.parse(date, formatter));
        }

        return dates;
    }
}