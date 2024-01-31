package edu.ucsb.cs156.spring.backenddemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.core.util.Json;
import com.fasterxml.jackson.databind.JsonNode;

@RestClientTest(LocationQueryService.class)
public class LocationQueryServiceTests {

    @Autowired
    private MockRestServiceServer mockRestServiceServer;

    @Autowired
    private LocationQueryService locationQueryService;

    @Test
    public void test_getLocations() throws URISyntaxException, UnsupportedEncodingException, JsonProcessingException {
        String location = "Seoul";
        String expectedURL = LocationQueryService.ENDPOINT.replace("{location}", location);

        String fakeJsonString = "{ \"fake\": \"result\" }";

        this.mockRestServiceServer.expect(requestTo(expectedURL))
                .andExpect(header("Accept", MediaType.APPLICATION_JSON.toString()))
                .andExpect(header("Content-Type", MediaType.APPLICATION_JSON.toString()))
                .andRespond(withSuccess(fakeJsonString, MediaType.APPLICATION_JSON));

        String actualResult = locationQueryService.getJSON(location);
        Json.mapper().writeValueAsString(fakeJsonString);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode expectedJsonNode = objectMapper.readTree(fakeJsonString);
        JsonNode actualJsonNode = objectMapper.readTree(actualResult);
        assertEquals(expectedJsonNode, actualJsonNode);
    }
}