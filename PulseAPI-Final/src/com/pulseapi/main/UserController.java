package com.pulseapi.main;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pulseapi.model.User;
import com.pulseapi.repo.UserCRUD;

@RestController
public class UserController {

    // REGISTER
    @PostMapping("/api/users/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {

        boolean success = UserCRUD.insertUserFromWeb(user);

        if (success) {
            return ResponseEntity.ok("User registered successfully!");
        }

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("Email already registered. Please use another email.");
    }


    // LOGIN
    @PostMapping("/api/users/login")
    public ResponseEntity<Map<String, Object>> loginUser(
            @RequestBody User user) {

        User existingUser =
                UserCRUD.loginUser(
                        user.getEmail(),
                        user.getPassword()
                );

        if (existingUser != null) {

            Map<String, Object> response =
                    new HashMap<>();

            response.put(
                    "message",
                    "Login successful!"
            );

            response.put(
                    "userId",
                    existingUser.getUserId()
            );

            response.put(
                    "name",
                    existingUser.getName()
            );

            response.put(
                    "email",
                    existingUser.getEmail()
            );

            return ResponseEntity.ok(response);

        } else {

            Map<String, Object> response =
                    new HashMap<>();

            response.put(
                    "message",
                    "Invalid email or password."
            );

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(response);
        }
    }
}