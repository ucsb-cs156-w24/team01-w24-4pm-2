package edu.ucsb.cs156.spring.backenddemo.controllers;

import edu.ucsb.cs156.spring.backenddemo.services.LocationQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/location")
public class LocationController {
    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    LocationQueryService locationQueryService;
    @Operation(summary = "Get location information", description = "Get location information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Location information"),
        @ApiResponse(responseCode = "400", description = "Invalid location"),
        @ApiResponse(responseCode = "404", description = "Location not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(value = "/{location}", produces = "application/json")
    public String getLocation(@PathVariable String location) {
        try {
            return locationQueryService.getJSON(location);
        } catch (HttpClientErrorException e) {
            log.error("Error: " + e.getMessage());
            return e.getMessage();
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return e.getMessage();
        }
    }
}