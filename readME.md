
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


First the request is coming to filter chain having user -crdential(as basic authentication) it decode the pasaword 
if the decorder is configured then req is coming to authenticationManager to validate the username from db by userDetailsService->loadUserByUsername(username);then db records for this user is
retrive then matched with pass credential if valida then req goes to controller class