package spring_security_model.security.mongodb;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MongoEmployeeServiceTest {

    @Mock
    private MongoEmployeeRepository mongoEmployeeRepository;

    @InjectMocks
    private MongoEmployeeService mongoEmployeeService;

    @Test
    void shouldCreateEmployee() {
        MongoEmployee employee = new MongoEmployee("Ali", "ali@gmail.com", "IT");
        when(mongoEmployeeRepository.existsByEmail("ali@gmail.com")).thenReturn(false);
        when(mongoEmployeeRepository.save(employee)).thenReturn(employee);

        MongoEmployee savedEmployee = mongoEmployeeService.createEmployee(employee);

        assertNotNull(savedEmployee);
        assertEquals("Ali", savedEmployee.getName());
        verify(mongoEmployeeRepository).save(employee);
    }

    @Test
    void shouldReturnAllEmployees() {
        MongoEmployee employee = new MongoEmployee("Ahmed", "ahmed@gmail.com", "HR");
        when(mongoEmployeeRepository.findAll()).thenReturn(List.of(employee));

        List<MongoEmployee> employees = mongoEmployeeService.getAllEmployees();

        assertEquals(1, employees.size());
        assertEquals("Ahmed", employees.get(0).getName());
    }

    @Test
    void shouldUpdateEmployee() {
        MongoEmployee existing = new MongoEmployee("Old Name", "old@gmail.com", "Sales");
        existing.setId("emp-1");

        MongoEmployee updated = new MongoEmployee("New Name", "new@gmail.com", "Engineering");
        when(mongoEmployeeRepository.findById("emp-1")).thenReturn(Optional.of(existing));
        when(mongoEmployeeRepository.save(existing)).thenReturn(existing);

        MongoEmployee result = mongoEmployeeService.updateEmployee("emp-1", updated);

        assertEquals("New Name", result.getName());
        assertEquals("Engineering", result.getDepartment());
    }

    @Test
    void shouldDeleteEmployee() {
        MongoEmployee existing = new MongoEmployee("Sam", "sam@gmail.com", "Finance");
        existing.setId("emp-2");
        when(mongoEmployeeRepository.findById("emp-2")).thenReturn(Optional.of(existing));

        mongoEmployeeService.deleteEmployee("emp-2");

        verify(mongoEmployeeRepository).delete(existing);
    }
}
