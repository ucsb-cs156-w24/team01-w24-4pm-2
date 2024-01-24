package edu.ucsb.cs156.spring.backenddemo.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ucsb.cs156.spring.backenddemo.services.EarthquakeQueryService;
import edu.ucsb.cs156.spring.backenddemo.services.JokeQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="Jokes from jokeapi.dev")
@Slf4j
@RestController
@RequestMapping("/api/jokes")
public class JokeController {
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    JokeQueryService jokeQueryService;

    @Operation(summary = "Get a certain number of jokes from a specified category", description = "JSON API endpoints/response format documented here: https://v2.jokeapi.dev/#endpoints")
    @GetMapping("/get")
    public ResponseEntity<String> getJoke(
            @Parameter(name="category", description="A valid joke category (Any, Misc, Programming, Dark, Pun, Spooky, Christmas)", example="any") @RequestParam String category,
            @Parameter(name="amount", description="Number of jokes you wish to retrieve", example="5") @RequestParam(name="amount") int numJokes
    ) throws JsonProcessingException {
        log.info("getJoke: category={} amount={}", category, numJokes);
        String result = jokeQueryService.getJSON(category, numJokes);
        return ResponseEntity.ok().body(result);
    }
}
