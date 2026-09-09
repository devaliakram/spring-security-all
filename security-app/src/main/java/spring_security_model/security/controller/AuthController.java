package spring_security_model.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import spring_security_model.security.dto.LoginRequest;
import spring_security_model.security.dto.LoginResponse;
import spring_security_model.security.jwt.JwtTokenProvider;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {

        /*
         * Contains the username and raw password received from the client.
         * At this point the user is not authenticated.
         */
        UsernamePasswordAuthenticationToken authenticationRequest = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

        /*
         * Triggers the Stage 2 authentication flow:
         *
         * AuthenticationManager
         *      ↓
         * DaoAuthenticationProvider
         *      ↓
         * CustomUserDetailsService
         *      ↓
         * AppUserRepository
         *      ↓
         * PasswordEncoder.matches()
         */
        Authentication authentication = authenticationManager.authenticate(authenticationRequest);

        /*
         * Runs only when username/password authentication succeeds.
         */
        String accessToken = jwtTokenProvider.generateToken(authentication);

        LoginResponse loginResponse = new LoginResponse(accessToken, "Bearer", jwtTokenProvider.getExpirationInSeconds());

        return ResponseEntity.ok(loginResponse);
    }
}
