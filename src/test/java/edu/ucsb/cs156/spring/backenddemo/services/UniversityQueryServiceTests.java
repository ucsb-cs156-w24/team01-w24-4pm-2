package edu.ucsb.cs156.spring.backenddemo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

@RestClientTest(UniversityQueryService.class)
public class UniversityQueryServiceTests {

    @Autowired
    private MockRestServiceServer mockRestServiceServer;

    @Autowired
    private UniversityQueryService universityQueryService;

    @Test
    public void test_getJSON() {

        String name = "Stanford";
        String expectedURL = UniversityQueryService.ENDPOINT.replace("{name}", name);

        String fakeJsonResult = "{\"name\": \"Stanford University\", \"alpha_two_code\": \"US\", \"country\": \"United States\", \"state-province\": null, \"domains\": [\"stanford.edu\"], \"web_pages\": [\"http://www.stanford.edu/\"]}";

        this.mockRestServiceServer.expect(requestTo(expectedURL))
                .andExpect(header("Accept", MediaType.APPLICATION_JSON.toString()))
                .andExpect(header("Content-Type", MediaType.APPLICATION_JSON.toString()))
                .andRespond(withSuccess(fakeJsonResult, MediaType.APPLICATION_JSON));

        String actualResult = universityQueryService.getJSON(name);
        assertEquals(fakeJsonResult, actualResult);
    }
    
}
