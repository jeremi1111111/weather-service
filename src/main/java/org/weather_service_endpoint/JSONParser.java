package org.weather_service_endpoint;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;
import java.util.List;
import java.util.stream.StreamSupport;

public class JSONParser {
    public static List<Double> parseDoubleList(JsonNode node) {
        return StreamSupport
                .stream(node.spliterator(), false)
                .map(JsonNode::asDouble)
                .toList();
    }

    public static List<Date> parseDateList(JsonNode node) {
        ObjectMapper mapper = new ObjectMapper();
        return StreamSupport
                .stream(node.spliterator(), false)
                .map(date -> mapper.convertValue(date, Date.class))
                .toList();
    }

    public static List<Integer> parseIntList(JsonNode node) {
        return StreamSupport
                .stream(node.spliterator(), false)
                .map(JsonNode::asInt)
                .toList();
    }
}
