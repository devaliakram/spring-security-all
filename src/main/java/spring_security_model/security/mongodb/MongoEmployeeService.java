package spring_security_model.security.mongodb;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MongoEmployeeService {

    private final MongoEmployeeRepository mongoEmployeeRepository;

    public MongoEmployeeService(MongoEmployeeRepository mongoEmployeeRepository) {
        this.mongoEmployeeRepository = mongoEmployeeRepository;
    }

    public MongoEmployee createEmployee(MongoEmployee employee) {
        if (mongoEmployeeRepository.existsByEmail(employee.getEmail())) {
            throw new IllegalArgumentException("Employee email already exists in MongoDB.");
        }

        return mongoEmployeeRepository.save(employee);
    }

    public List<MongoEmployee> getAllEmployees() {
        return mongoEmployeeRepository.findAll();
    }

    public MongoEmployee getEmployeeById(String id) {
        return mongoEmployeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with id: " + id));
    }

    public MongoEmployee updateEmployee(String id, MongoEmployee updatedEmployee) {
        MongoEmployee existingEmployee = getEmployeeById(id);
        existingEmployee.setName(updatedEmployee.getName());
        existingEmployee.setEmail(updatedEmployee.getEmail());
        existingEmployee.setDepartment(updatedEmployee.getDepartment());
        return mongoEmployeeRepository.save(existingEmployee);
    }

    public void deleteEmployee(String id) {
        MongoEmployee employee = getEmployeeById(id);
        mongoEmployeeRepository.delete(employee);
    }
}
