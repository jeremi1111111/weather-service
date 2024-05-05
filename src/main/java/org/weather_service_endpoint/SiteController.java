package org.weather_service_endpoint;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Controller
public class SiteController {
    private final static String weatherApiAddress = "https://api.open-meteo.com/v1/forecast";
    private final static String forecastParameters = "daily=weather_code,temperature_2m_max,temperature_2m_min,sunshine_duration&timezone=auto";

    @GetMapping(value = "/forecast")
    @ResponseBody
    public List<DailyForecast> homePage(
            @RequestParam("latitude") double latitude,
            @RequestParam("longitude") double longitude) throws IOException {
        if (latitude < -90 || latitude > 90) {
            throw new IllegalArgumentException("Latitude must be in range [-90,90], is " + latitude);
        }
        if (longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException("Longitude must be in range [-180,180], is " + longitude);
        }

        String uri = weatherApiAddress + "?latitude=" + latitude + "&longitude=" + longitude + "&" + forecastParameters;
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        JsonNode rootNode = mapper.readTree(result);
        JsonNode dailyNode = rootNode.get("daily");

        WeatherForecast forecast = new WeatherForecast(latitude, longitude);
        forecast.setDate(JSONParser.parseDateList(dailyNode.get("time")));
        forecast.setWeatherCode(JSONParser.parseIntList(dailyNode.get("weather_code")));
        forecast.setMinTemperature(JSONParser.parseDoubleList(dailyNode.get("temperature_2m_min")));
        forecast.setMaxTemperature(JSONParser.parseDoubleList(dailyNode.get("temperature_2m_max")));
        forecast.setSunshineDuration(JSONParser.parseDoubleList(dailyNode.get("sunshine_duration")));

        return forecast.getDailyForecasts();
    }
}
