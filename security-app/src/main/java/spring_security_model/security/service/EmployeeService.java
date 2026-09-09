package spring_security_model.security.service;

import org.springframework.stereotype.Service;
import spring_security_model.security.dao.EmployeeRepository;
import spring_security_model.security.entity.Employee;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee createEmployee(Employee employee) {
        if (employeeRepository.existsByEmail(employee.getEmail())) {
            throw new IllegalArgumentException(
                    "Employee email already exists."
            );
        }

        return employeeRepository.save(employee);
    }

    public List<Employee> createEmployees(List<Employee> employees) {
        return employeeRepository.saveAll(employees);
    }

    public Employee getOneEmployee(Long employeeId) {
        return employeeRepository.findById(employeeId).orElseThrow(() -> new IllegalStateException("Employee is not found"));
    }

    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

    public Employee updateEmployee(Long id, Employee newEmployee) {

        Employee oldEmployee = getOneEmployee(id);
        oldEmployee.setName(newEmployee.getName());
        oldEmployee.setEmail(newEmployee.getEmail());
        oldEmployee.setDepartment(newEmployee.getDepartment());
        return employeeRepository.save(oldEmployee);

    }

    public void deleteEmployee(Long id) {
        Employee oneEmployee = getOneEmployee(id);
        employeeRepository.delete(oneEmployee);
    }

}
