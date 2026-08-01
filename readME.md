THIS IS REGARDING STAGE-3 WHERE WE WILL BE USING JWT TOKEN BASED SECURITY  

Stage 3: Generate JWT after login
    
    In Stage 2, you used Basic Authentication for every secured request.
    
    That means every request sends:
    
    Username: ali
    Password: ali123
    
    In Stage 3, the username and password are sent only to a dedicated login API. After successful authentication, the server generates a JWT and returns it to the client.
    
    Then later requests use:
    
    Authorization: Bearer <jwt-token>
    
    instead of sending the password every time.
    
    Stage 2 vs Stage 3
==================================

    Stage 2
    Every request
    ↓
    Username + password
    ↓
    Database authentication
    ↓
    Controller
=========================================

    Stage 3
    Login request only
    ↓
    Username + password
    ↓
    Database authentication
    ↓
    Generate JWT
    ↓
    Return JWT

    Then:
    
    Other API request
    ↓
    JWT token
    ↓
    Validate JWT
    ↓
    Controller
    
    At Stage 3, we are focusing only on:
    
    Authenticate the login request and generate a token.
    
    The JWT filter that validates tokens on later requests comes in Stage 4.
------------------------------------------------------------------------------------------------------------------------------

        Complete Stage 3 login flow
        POST /auth/login
        ↓
        LoginRequest contains username and password
        ↓
        AuthController
        ↓
        AuthenticationManager.authenticate(...)
        ↓
        DaoAuthenticationProvider
        ↓
        CustomUserDetailsService
        ↓
        AppUserRepository
        ↓
        app_users table
        ↓
        PasswordEncoder compares password
        ↓
        Authentication successful
        ↓
        JwtTokenProvider generates JWT
        ↓
        LoginResponse returned to client

------------------------------------------------------------------------------------------------------------------------------------

Interview explanation

In the earlier stage, Spring Security used Basic Authentication, so its built-in filter read the username and password on every request. After introducing JWT, the username and password are validated only during login, and the server returns a signed token.

For subsequent requests, Spring Security does not automatically understand that token, so I added a custom OncePerRequestFilter. The filter reads the Bearer token from the Authorization header, validates its signature and expiration, extracts the username, loads the corresponding user details, creates an authenticated UsernamePasswordAuthenticationToken, and stores it in SecurityContextHolder.

I registered the filter before UsernamePasswordAuthenticationFilter and configured stateless session management. This allows Spring Security’s authorization rules to recognize the JWT-authenticated user without sending the password again.

Easy memory line:

Stage 2 validates password on every Basic request, Stage 3 generates JWT after login, and Stage 4 converts that JWT into SecurityContext authentication for every protected request.