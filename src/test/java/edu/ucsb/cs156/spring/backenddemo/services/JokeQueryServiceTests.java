package edu.ucsb.cs156.spring.backenddemo.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(JokeQueryServiceTests.class)
public class JokeQueryServiceTests {

    @Autowired
    private MockRestServiceServer mockRestServiceServer;

    @Autowired
    private JokeQueryService jokeQueryService;

    @Test
    public void test_getJSON() {

        String category = "any";
        int numJokes = 1;
        String expectedURL = JokeQueryService.ENDPOINT.replace("{category}", category).replace("{numJokes}", Integer.toString(numJokes));

        String fakeJsonResult = "{\n" +
                "    \"error\": false,\n" +
                "    \"category\": \"Programming\",\n" +
                "    \"type\": \"twopart\",\n" +
                "    \"setup\": \"Why do programmers prefer using the dark mode?\",\n" +
                "    \"delivery\": \"Because light attracts bugs.\",\n" +
                "    \"flags\": {\n" +
                "        \"nsfw\": false,\n" +
                "        \"religious\": false,\n" +
                "        \"political\": false,\n" +
                "        \"racist\": false,\n" +
                "        \"sexist\": false,\n" +
                "        \"explicit\": false\n" +
                "    },\n" +
                "    \"id\": 232,\n" +
                "    \"safe\": true,\n" +
                "    \"lang\": \"en\"\n" +
                "}";

        this.mockRestServiceServer.expect(requestTo(expectedURL))
                .andExpect(header("Accept", MediaType.APPLICATION_JSON.toString()))
                .andExpect(header("Content-Type", MediaType.APPLICATION_JSON.toString()))
                .andRespond(withSuccess(fakeJsonResult, MediaType.APPLICATION_JSON));

        String actualResult = jokeQueryService.getJSON(category, numJokes);
        assertEquals(fakeJsonResult, actualResult);
    }
}


