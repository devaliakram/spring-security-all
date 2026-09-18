package spring_security_model.security.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MongoEmployeeRepository extends MongoRepository<MongoEmployee, String> {

    boolean existsByEmail(String email);

    Optional<MongoEmployee> findByEmail(String email);
}
