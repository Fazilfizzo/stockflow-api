package com.fizoind.stockflow_api.authentication;

import com.fizoind.stockflow_api.authentication.dto.AuthResponse;
import com.fizoind.stockflow_api.authentication.dto.LoginRequest;
import com.fizoind.stockflow_api.authentication.dto.RegisterRequest;
import com.fizoind.stockflow_api.authentication.refreshToken.RefreshRequest;
import com.fizoind.stockflow_api.authentication.refreshToken.RefreshTokenService;
import com.fizoind.stockflow_api.user.User;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    public AuthController(AuthService authService, RefreshTokenService refreshTokenService) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        authService.register(registerRequest);
        return ResponseEntity.ok("Registered successfully...........");
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody RefreshRequest refreshRequest) {
        return new ResponseEntity<>(refreshTokenService.refreshAccessAndRefreshToken(refreshRequest), HttpStatusCode.valueOf(200));
    }
}
