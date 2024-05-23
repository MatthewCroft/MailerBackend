package com.croft.mailerbackend.controllers;

import ch.qos.logback.core.status.Status;
import com.croft.mailerbackend.ml.BARTService;
import com.croft.mailerbackend.models.ModelRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BARTController {

    @Autowired
    private BARTService bartService;

    @PostMapping(path = "/bart", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createTextSummary(@RequestBody ModelRequest modelRequest) {
        String summary;

        try {
            summary = bartService.summarizeText(modelRequest.getInputs());
        } catch (Exception e) {
            return ResponseEntity
                    .status(Status.ERROR)
                    .body(e.getMessage());
        }

        return ResponseEntity
                .ok(summary);
    }
}
