package com.fizoind.stockflow_api.authentication.refreshToken;

import com.fizoind.stockflow_api.authentication.CustomUserDetails;
import com.fizoind.stockflow_api.authentication.JwtService;
import com.fizoind.stockflow_api.authentication.dto.AuthResponse;
import com.fizoind.stockflow_api.user.User;
import com.fizoind.stockflow_api.user.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Service
public class RefreshTokenService {

   private final RefreshTokenRepository refreshTokenRepository;
   private final UserRepository userRepository;
   private final JwtService jwtService;
   private final PasswordEncoder passwordEncoder;


    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public String createToken(String username) {
        String tokenId = UUID.randomUUID().toString();
        String secret = UUID.randomUUID().toString() + UUID.randomUUID().toString();

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUsername(username);
        refreshToken.setToken(BCrypt.hashpw(secret, BCrypt.gensalt()));
        refreshToken.setExpiresAt(Instant.now().plus(7, ChronoUnit.DAYS));
        refreshToken.setRevoked(false);

        refreshTokenRepository.save(refreshToken);

        return tokenId + "." + secret;
    }

    public RefreshToken validate(String refreshToken) {
        String[] parts = refreshToken.split("\\.");

        if (parts.length != 2) {
            throw new RuntimeException("Invalid refresh token format");
        }

        String tokenId = parts[0];
        String secret = parts[1];

        RefreshToken storedToken = refreshTokenRepository.findByTokenId(tokenId).orElseThrow(() -> new RuntimeException("Refresh token not found"));

        if (storedToken.isRevoked()) {
            throw new RuntimeException("Refresh token revoked.");
        }

        if (storedToken.getExpiresAt().isBefore(Instant.now())) {
            throw new RuntimeException("Refresh token expired");
        }

        boolean matches = passwordEncoder.matches(
                secret,
                storedToken.getToken()
        );

        if (!matches) {
            throw new RuntimeException("Invalid refresh token");
        }

        return storedToken;
    }

    public AuthResponse refreshAccessAndRefreshToken(RefreshRequest refreshRequest) {

       RefreshToken storedToken = validate(refreshRequest.getRefreshToken());

       User user = userRepository.findByUsername(storedToken.getUsername()).orElseThrow(() -> new RuntimeException("Username not found"));

       String newAccessToken = jwtService.generateToken(new CustomUserDetails(user));

        storedToken.setRevoked(true);
        refreshTokenRepository.save(storedToken);

        String newRefreshToken = createToken(user.getUsername());

        return new AuthResponse(newAccessToken, newRefreshToken);
    }
}
