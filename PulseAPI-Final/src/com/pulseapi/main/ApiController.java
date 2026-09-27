package com.pulseapi.main;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pulseapi.model.Api;
import com.pulseapi.repo.ApiCRUD;

@RestController
public class ApiController {

    // ADD API
    @PostMapping("/api/apis")
    public ResponseEntity<String> addApi(
            @RequestBody Api api) {

        ApiCRUD.insertApi(api);

        return ResponseEntity.ok(
                "API added successfully!"
        );
    }


    // GET APIs for a user
    @GetMapping("/api/apis")
    public ResponseEntity<List<Api>> getUserApis(
            @RequestParam long userId) {

        List<Api> apis =
                ApiCRUD.getApisByUserId(userId);

        return ResponseEntity.ok(apis);
    }


    // DELETE API
    @DeleteMapping("/api/apis/{apiId}")
    public ResponseEntity<String> deleteApi(
            @PathVariable long apiId) {

        ApiCRUD.deleteApi(apiId);

        return ResponseEntity.ok(
                "API deleted successfully!"
        );
    }
    // GET all APIs for monitoring
    @GetMapping("/api/apis/all")
    public ResponseEntity<List<Api>> getAllApis() {

        List<Api> apis =
                ApiCRUD.getAllApisForMonitoring();

        return ResponseEntity.ok(apis);
    }
}