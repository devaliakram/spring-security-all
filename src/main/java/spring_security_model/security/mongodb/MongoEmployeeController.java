package spring_security_model.security.mongodb;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mongo/employees")
public class MongoEmployeeController {

    private final MongoEmployeeService mongoEmployeeService;

    public MongoEmployeeController(MongoEmployeeService mongoEmployeeService) {
        this.mongoEmployeeService = mongoEmployeeService;
    }

    @PostMapping
    public ResponseEntity<MongoEmployee> createEmployee(@Valid @RequestBody MongoEmployee employee) {
        MongoEmployee savedEmployee = mongoEmployeeService.createEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
    }

    @GetMapping
    public ResponseEntity<List<MongoEmployee>> getAllEmployees() {
        return ResponseEntity.ok(mongoEmployeeService.getAllEmployees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MongoEmployee> getEmployee(@PathVariable String id) {
        return ResponseEntity.ok(mongoEmployeeService.getEmployeeById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MongoEmployee> updateEmployee(@PathVariable String id, @Valid @RequestBody MongoEmployee employee) {
        return ResponseEntity.ok(mongoEmployeeService.updateEmployee(id, employee));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String id) {
        mongoEmployeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
