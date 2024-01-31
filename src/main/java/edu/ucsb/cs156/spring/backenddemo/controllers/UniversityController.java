package edu.ucsb.cs156.spring.backenddemo.controllers;

import org.springframework.web.bind.annotation.RestController;

import edu.ucsb.cs156.spring.backenddemo.services.UniversityQueryService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name="University info from Hipo")
@Slf4j
@RestController
@RequestMapping("/api/university")
public class UniversityController {
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    UniversityQueryService universityQueryService;

    @Operation(summary = "Provides a search endpoint you can search for an autocomplete for university name or/and filter by country.", description = "JSON return format documented here: https://github.com/Hipo/university-domains-list-api")
    @GetMapping("/get")
    public ResponseEntity<String> getEarthquakes(
        @Parameter(name="name", description="Name of a University", example="Stanford") @RequestParam String name
    ) throws JsonProcessingException {
        log.info("getUniviersities: name={}", name);
        String result = universityQueryService.getJSON(name);
        return ResponseEntity.ok().body(result);
    }
}
