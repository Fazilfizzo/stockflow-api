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
        String rawToken = UUID.randomUUID().toString();
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUsername(username);
        refreshToken.setToken(BCrypt.hashpw(rawToken, BCrypt.gensalt()));
        refreshToken.setExpiryDate(new Date(System.currentTimeMillis() + 7L * 24 * 60 * 60 * 1000));
        refreshToken.setRevoked(false);

        refreshTokenRepository.save(refreshToken);

        return rawToken;
    }

    public void validate(String refreshToken) {
        if(passwordEncoder.matches(refreshToken, token.getToken())) {

        };
    }

    public AuthResponse refreshAccessAndRefreshToken(RefreshRequest refreshRequest) {
        RefreshToken refreshToken1 = refreshTokenRepository.findByToken(refreshRequest.getRefreshToken()).orElseThrow(() -> new RuntimeException("token invalid"));

        validate(refreshRequest.getRefreshToken());

        User user = userRepository.findByUsername(refreshToken1.getUsername()).orElseThrow(()-> new UsernameNotFoundException(refreshToken1.getUsername()));

        String newAccessToken = jwtService.generateToken(new CustomUserDetails(user));

        refreshToken1.setRevoked(true);
        refreshTokenRepository.save(refreshToken1);

        String newRefreshToken = createToken(user.getUsername());

        return new AuthResponse(newAccessToken, newRefreshToken);
    }
}
