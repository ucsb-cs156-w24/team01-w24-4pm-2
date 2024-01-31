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

@RestClientTest(LocationQueryService.class)
public class LocationQueryServiceTests {

    @Autowired
    private MockRestServiceServer mockRestServiceServer;

    @Autowired
    private LocationQueryService locationQueryService;

    @Test
    public void test_getLocations() throws URISyntaxException, UnsupportedEncodingException, JsonProcessingException {
        String location = "University of California - Santa Barbara";
        String expectedURL = LocationQueryService.ENDPOINT.replace("{location}", URLEncoder.encode(location, StandardCharsets.UTF_8.toString()));

        String fakeJsonString = "{ \"fake\": \"data\" }";

        this.mockRestServiceServer.expect(requestTo(expectedURL))
                .andExpect(header("Accept", "text/plain"))
                .andExpect(header("Content-Type", MediaType.APPLICATION_JSON.toString()))
                .andRespond(withSuccess(fakeJsonString, MediaType.TEXT_PLAIN));

        String actualResult = locationQueryService.getJSON();
        assertEquals(fakeJsonString, actualResult);
    }

}
