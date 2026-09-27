package com.pulseapi.main;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pulseapi.model.MonitoringResult;
import com.pulseapi.repo.MonitoringResultCRUD;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class MonitoringResultController {

    // Get all monitoring results
    @GetMapping("/api/monitoring-results")
    public ResponseEntity<List<MonitoringResult>> getMonitoringResults(
            @RequestParam long userId) {

        MonitoringResultCRUD crud =
                new MonitoringResultCRUD();

        List<MonitoringResult> results =
                crud.getMonitoringResultsByUserId(userId);

        return ResponseEntity.ok(results);
    }
}