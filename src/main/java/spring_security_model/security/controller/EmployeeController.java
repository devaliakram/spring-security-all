package spring_security_model.security.controller;


import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring_security_model.security.dao.EmployeeRepository;
import spring_security_model.security.entity.Employee;
import spring_security_model.security.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/employees")
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;


    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<Employee>> createMultipleEmployee(@RequestBody List<Employee> employee) {
        List<Employee> employees = employeeService.createEmployees(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(employees);
    }

    @PostMapping("/create")
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) {
        Employee serviceEmployee = employeeService.createEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceEmployee);
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getOneEmployee(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getOneEmployee(id));
    }


    @GetMapping("/all")
    public ResponseEntity<List<Employee>> getAllEmployee() {
        return ResponseEntity.ok(employeeService.getAllEmployee());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        log.info("getting called delete api");
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update{id}")
    public ResponseEntity<Employee> updateEmployee(Long id, Employee employee) {
        log.info("getting called delete api");
        return ResponseEntity.ok(employeeService.updateEmployee(id, employee));
    }
}
