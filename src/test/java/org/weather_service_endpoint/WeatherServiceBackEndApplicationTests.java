package org.weather_service_endpoint;

import org.junit.jupiter.api.Test;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MissingServletRequestParameterException;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class WeatherServiceBackEndApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void givenNullParameters_whenGetForecast_thenThrowsMissingServletRequestParameterException() throws Exception {
        String expectedResponse = "{"
                + "\"url\":\"/forecast\","
                + "\"message\":\"Missing parameter 'latitude' of type 'double'.\""
                + "}";
        String expectedContentType = "application/json";
        this.mockMvc.perform(get("/forecast"))
                .andExpect(result -> assertInstanceOf(MissingServletRequestParameterException.class, result.getResolvedException()))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals(expectedContentType, result.getResponse().getContentType()))
                .andExpect(result -> assertEquals(expectedResponse, result.getResponse().getContentAsString()));
    }

    @Test
    void givenLatitude_whenGetForecast_thenThrowsMissingServletRequestParameterException() throws Exception {
        String expectedResponse = "{"
                + "\"url\":\"/forecast\","
                + "\"message\":\"Missing parameter 'longitude' of type 'double'.\""
                + "}";
        String expectedContentType = "application/json";
        this.mockMvc.perform(get("/forecast?latitude=10"))
                .andExpect(result -> assertInstanceOf(MissingServletRequestParameterException.class, result.getResolvedException()))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals(expectedContentType, result.getResponse().getContentType()))
                .andExpect(result -> assertEquals(expectedResponse, result.getResponse().getContentAsString()));
    }

    @Test
    void givenLongitude_whenGetForecast_thenThrowsMissingServletRequestParameterException() throws Exception {
        String expectedResponse = "{"
                + "\"url\":\"/forecast\","
                + "\"message\":\"Missing parameter 'latitude' of type 'double'.\""
                + "}";
        String expectedContentType = "application/json";
        this.mockMvc.perform(get("/forecast?longitude=10"))
                .andExpect(result -> assertInstanceOf(MissingServletRequestParameterException.class, result.getResolvedException()))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals(expectedContentType, result.getResponse().getContentType()))
                .andExpect(result -> assertEquals(expectedResponse, result.getResponse().getContentAsString()));
    }

    @Test
    void givenWrongTypeLatitude_whenGetForecast_thenThrowsTypeMismatchException() throws Exception {
        String expectedResponse = "{"
                + "\"url\":\"/forecast\","
                + "\"message\":\"Incorrect value 'abc' for parameter 'latitude' of type 'double'.\""
                + "}";
        String expectedContentType = "application/json";
        this.mockMvc.perform(get("/forecast?latitude=abc"))
                .andExpect(result -> assertInstanceOf(TypeMismatchException.class, result.getResolvedException()))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals(expectedContentType, result.getResponse().getContentType()))
                .andExpect(result -> assertEquals(expectedResponse, result.getResponse().getContentAsString()));
    }

    @Test
    void givenLatitudeOutOfRange_whenGetForecast_thenThrowsIllegalArgumentException() throws Exception {
        String expectedResponse = "{"
                + "\"url\":\"/forecast\","
                + "\"message\":\"Latitude must be in range [-90,90], is 100.0\""
                + "}";
        String expectedContentType = "application/json";
        this.mockMvc.perform(get("/forecast?latitude=100&longitude=10"))
                .andExpect(result -> assertInstanceOf(IllegalArgumentException.class, result.getResolvedException()))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals(expectedContentType, result.getResponse().getContentType()))
                .andExpect(result -> assertEquals(expectedResponse, result.getResponse().getContentAsString()));
    }
}
