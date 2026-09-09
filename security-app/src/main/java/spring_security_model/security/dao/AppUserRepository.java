package spring_security_model.security.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import spring_security_model.security.entity.AppUser;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByUsername(String username);

    boolean existsByUsername(String username);
}
