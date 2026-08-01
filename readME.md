
** In Stage 2, the user is stored in the database:

app_users table

Spring Security now loads the username, password, and role from that table by using your custom UserDetailsService.

**


        Complete Stage 2 flow
        Postman sends username and password
        ↓
        BasicAuthenticationFilter
        ↓
        AuthenticationManager
        ↓
        DaoAuthenticationProvider
        ↓
        CustomUserDetailsService
        ↓
        AppUserRepository
        ↓
        app_users table
        ↓
        SecurityUser / UserDetails
        ↓
        BCryptPasswordEncoder
        ↓
        Authentication object
        ↓
        SecurityContextHolder
        ↓
        Authorization rules
        ↓
        Employee Controller