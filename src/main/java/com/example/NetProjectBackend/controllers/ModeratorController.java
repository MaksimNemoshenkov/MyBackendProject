package com.example.NetProjectBackend.controllers;

import com.example.NetProjectBackend.models.entity.User;
import com.example.NetProjectBackend.service.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/moderator")
@CrossOrigin(origins = "*") 
@AllArgsConstructor
public class ModeratorController {

    private final UserServiceImpl userServiceImpl;
    /** Create only moderator, and send new random password to email */
    @PostMapping
    public ResponseEntity<?> createModerator(@RequestBody User user) {
        return ResponseEntity.ok(userServiceImpl.createModerator(user));
    }

    @PutMapping
    public void updateModerator(@RequestBody User user) {
        userServiceImpl.update(user);
    }

    @DeleteMapping("/{id}")
    public void deleteModerator(@PathVariable int id) {
        userServiceImpl.delete(id);
    }

}
