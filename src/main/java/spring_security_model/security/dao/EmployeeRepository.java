package spring_security_model.security.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import spring_security_model.security.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsByEmail(String email);
}