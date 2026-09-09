package spring_security_model.security.config;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import spring_security_model.security.dao.AppUserRepository;
import spring_security_model.security.entity.AppUser;

/**
 * This class for inserting one role data on db, we can manully insert data on db
 * but this is a good way to insert data on db when we run the application for the first time.
 */
@Configuration
public class InitialAppUserConfiguration {

    @Bean
    public CommandLineRunner createInitialAppUser( AppUserRepository appUserRepository,PasswordEncoder passwordEncoder) {
        return args -> {

            if (!appUserRepository.existsByUsername("ali")) {

                AppUser appUser = new AppUser();

                appUser.setUsername("ali");
                appUser.setPassword(
                        passwordEncoder.encode("ali123")
                );
                appUser.setRole("ADMIN");
                appUser.setEnabled(true);

                appUserRepository.save(appUser);

                System.out.println(
                        "Initial app user created: ali"
                );
            }
        };
    }
}
