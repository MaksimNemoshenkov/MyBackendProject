package com.example.NetProjectBackend.controllers;

import com.example.NetProjectBackend.models.UserRecovery;
import com.example.NetProjectBackend.models.dto.JwtResponse;
import com.example.NetProjectBackend.models.dto.LoginRequest;
import com.example.NetProjectBackend.models.entity.User;
import com.example.NetProjectBackend.models.enums.ERole;
import com.example.NetProjectBackend.service.UserDetailsImpl;
import com.example.NetProjectBackend.service.UserService;
import com.example.NetProjectBackend.service.jwt.JwtUtils;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final UserService userService;


    @PostMapping("/login")
    public ResponseEntity<?> authUser(@RequestBody String  login) {
        LoginRequest loginRequest = new Gson().fromJson(login, LoginRequest.class);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword());
        Authentication authentication = authenticationManager
                .authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        System.out.println(userDetails);
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getFirstname(),
                userDetails.getLastname(),
                userDetails.getStatus(),
                userDetails.getTimestamp(),
                userDetails.getImageId(),
                userDetails.getRole()));
    }

    @RequestMapping(method = RequestMethod.POST, path = "/signup")
    public ResponseEntity<?> registerUser(@RequestBody User signupRequest) {
        signupRequest.setRole(ERole.USER.getAuthority());
        return ResponseEntity.ok(userService.create(signupRequest));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/code")
    public ResponseEntity<?> code(@RequestParam String param) {
        return userService.code(param);
    }

    @RequestMapping(method = RequestMethod.POST, path="/recovery")
    public ResponseEntity<?> recoveryPassword(@RequestBody UserRecovery userRecovery) {
        return userService.recovery(userRecovery.getEmail());
    }

}
