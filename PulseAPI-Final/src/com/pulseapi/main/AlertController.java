package com.pulseapi.main;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pulseapi.model.Alert;
import com.pulseapi.repo.AlertCRUD;

@RestController
public class AlertController {

    // Get alerts for logged-in user
    @GetMapping("/api/alerts")
    public ResponseEntity<List<Alert>> getUserAlerts(
            @RequestParam long userId) {

        AlertCRUD alertCRUD =
                new AlertCRUD();

        List<Alert> alerts =
                alertCRUD.getAlertsByUserId(userId);

        return ResponseEntity.ok(alerts);
    }
}