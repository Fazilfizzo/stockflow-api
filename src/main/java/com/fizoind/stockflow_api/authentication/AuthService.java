package com.fizoind.stockflow_api.authentication;

import com.fizoind.stockflow_api.authentication.dto.AuthResponse;
import com.fizoind.stockflow_api.authentication.dto.LoginRequest;
import com.fizoind.stockflow_api.authentication.dto.RegisterRequest;
import com.fizoind.stockflow_api.authentication.refreshToken.RefreshTokenService;
import com.fizoind.stockflow_api.customer.entity.Customer;
import com.fizoind.stockflow_api.customer.repository.CustomerRepository;
import com.fizoind.stockflow_api.user.Role;
import com.fizoind.stockflow_api.user.User;
import com.fizoind.stockflow_api.user.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public AuthService(UserRepository userRepository, CustomerRepository customerRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService, RefreshTokenService refreshTokenService) {
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    public void register(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.setRole(Role.ROLE_USER);
        User saved_user =  userRepository.save(user);

        Customer customer = new Customer();
        customer.setName(registerRequest.getUsername());
        customer.setPhone(registerRequest.getPhoneNumber());
        customer.setEmail(registerRequest.getEmail());
        customer.setAddress(registerRequest.getAddress());
        customer.setUser(saved_user);
        customerRepository.save(customer);
    }

    public AuthResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(() -> new UsernameNotFoundException("user not found"));

        String accessToken = jwtService.generateToken(new CustomUserDetails(user));

        String refreshToken = refreshTokenService.createToken(user.getUsername());

        return new AuthResponse(accessToken, refreshToken);
    }
}
