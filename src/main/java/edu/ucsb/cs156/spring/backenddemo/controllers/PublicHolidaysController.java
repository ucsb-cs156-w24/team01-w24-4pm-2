package edu.ucsb.cs156.spring.backenddemo.controllers;

import edu.ucsb.cs156.spring.backenddemo.services.PublicHolidayQueryService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name="Public Holiday Info from https://date.nager.at/Api")
@Slf4j
@RestController
@RequestMapping("/api/publicholidays")
public class PublicHolidaysController {

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    PublicHolidayQueryService publicHolidaysQueryService;

    @Operation(summary="Get public holidays for a given year and country", description ="JSON return format documented here: https://date.nager.at/Api")
    @GetMapping("/get")
    public ResponseEntity<String> getCountryCodes(
        @Parameter(name="countryCode", description="2 letter country code", example="US, MX, CN") @RequestParam String countryCode,
        @Parameter(name="year", description="The current year we are in", example="2012") @RequestParam String year
    ) throws JsonProcessingException {
        log.info("getCountryCodes: countryCode={} year={}", countryCode, year);
        String result = publicHolidaysQueryService.getJSON(year, countryCode);
        return ResponseEntity.ok().body(result);
    }

}