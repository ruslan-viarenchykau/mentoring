package com.ruslan.mentoring.jpa.services;

import com.ruslan.mentoring.jpa.models.Employee;
import com.ruslan.mentoring.jpa.models.Project;
import com.ruslan.mentoring.jpa.models.Unit;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeManagerService extends EntityManagerService {
    @Transactional
    public boolean addToUnit(Long employeeId, Long unitId) {
        Employee employee = find(Employee.class, employeeId);
        Unit unit = find(Unit.class, unitId);

        if (employee == null || unit == null) {
            return false;
        }

        unit.getEmployees().add(employee);
        update(unit);
        return true;
    }

    @Transactional
    public boolean assignEmployeeForProject(Long employeeId, Long projectId) {
        Employee employee = find(Employee.class, employeeId);
        Project project = find(Project.class, projectId);

        if (employee != null && project != null && !employee.getProjects().contains(project)) {
            employee.getProjects().add(project);
            update(employee);
            return true;
        }
        return false;
    }
}
