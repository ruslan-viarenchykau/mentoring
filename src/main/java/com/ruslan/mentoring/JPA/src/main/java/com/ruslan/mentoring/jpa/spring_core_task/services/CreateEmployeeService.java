package com.ruslan.mentoring.jpa.spring_core_task.services;

import com.ruslan.mentoring.jpa.models.Employee;
import org.springframework.stereotype.Service;

@Service
public class CreateEmployeeService {
    public Employee createEmployee() {
        Employee employee = new Employee();
        employee.setFirstName("Alex");
        employee.setLastName("Johnson");
        return employee;
    }
}
