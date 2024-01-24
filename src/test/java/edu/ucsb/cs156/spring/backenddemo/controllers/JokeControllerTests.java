package edu.ucsb.cs156.spring.backenddemo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ucsb.cs156.spring.backenddemo.services.JokeQueryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import edu.ucsb.cs156.spring.backenddemo.services.EarthquakeQueryService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(value = JokeController.class)
public class JokeControllerTests {
    private ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    JokeQueryService mockJokeQueryService;


    @Test
    public void test_getJoke() throws Exception {

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
        String category = "any";
        int numJokes = 1;
        when(mockJokeQueryService.getJSON(eq(category),eq(numJokes))).thenReturn(fakeJsonResult);

        String url = String.format("/api/jokes/get?category=%s&amount=%s",category,numJokes);

        MvcResult response = mockMvc
                .perform( get(url).contentType("application/json"))
                .andExpect(status().isOk()).andReturn();

        String responseString = response.getResponse().getContentAsString();

        assertEquals(fakeJsonResult, responseString);
    }

}