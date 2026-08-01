package spring_security_model.security.entrysecurity;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spring_security_model.security.dao.AppUserRepository;
import spring_security_model.security.entity.AppUser;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    public CustomUserDetailsService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

      AppUser appUser=  appUserRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User is not found " +username));
        return new SecurityUser(appUser);
    }
}
