package com.ruslan.mentoring.jpa.controllers;

import com.ruslan.mentoring.jpa.models.Employee;
import com.ruslan.mentoring.jpa.models.Project;
import com.ruslan.mentoring.jpa.models.Unit;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/employee")
public class EmployeeController extends AbstractController {
    private static final String ENTITY_KEY = "employee";

    /*   F I N D   */

    @RequestMapping(method = RequestMethod.GET, path = "read")
    public String find(Model model, @RequestParam Long id) {
        Employee employee = entityManager.find(Employee.class, id);
        if (employee == null) {
            model.addAttribute(MESSAGE_KEY, "Employee not found");
            return "index";
        } else {
            model.addAttribute(ENTITY_KEY, employee);
            return "employee/employee.view";
        }
    }

    /*   C R E A T E   */

    @RequestMapping(method = RequestMethod.GET, path = "create")
    public String getCreateForm(Model model, Employee employee) {
        return "employee/employee.create";
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST, path = "create")
    public String create(Model model, @ModelAttribute Employee employee) {
        entityManager.persist(employee);
        model.addAttribute(MESSAGE_KEY, "Employee created");
        return "index";
    }

    /*   U P D A T E   */

    @RequestMapping(method = RequestMethod.GET, path = "update")
    public String getUpdateForm(Model model, Employee employee, @RequestParam Long id) {
        employee = entityManager.find(Employee.class, id);
        if (employee == null) {
            model.addAttribute(MESSAGE_KEY, "Employee not found");
            return "index";
        } else {
            model.addAttribute(ENTITY_KEY, employee);
            return "employee/employee.update";
        }
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST, path = "update")
    public String update(Model model, @ModelAttribute Employee employee) {
        Employee employeeById = entityManager.find(Employee.class, employee.getId());
        if (employeeById != null) {
            entityManager.merge(employee);
            model.addAttribute(MESSAGE_KEY, "Employee updated");
        } else {
            model.addAttribute(MESSAGE_KEY, "Employee not found");
        }
        return "index";
    }

    /*   D E L E T E   */

    @Transactional
    @RequestMapping(method = RequestMethod.POST, path = "delete")
    public String delete(Model model, @RequestParam Long id) {
        Employee employee = entityManager.find(Employee.class, id);
        if (employee != null) {
            entityManager.remove(employee);
            model.addAttribute(MESSAGE_KEY, "Employee deleted");
        } else {
            model.addAttribute(MESSAGE_KEY, "Employee not found");
        }
        return "index";
    }

    /*   A D D   T O   U N I T   */

    @RequestMapping(method = RequestMethod.GET, path = "addToUnit")
    public String getAddToUnitForm(Model model) {
        List<Long> employeesIds = getEmployeesIds(getAll(Employee.class));
        List<Long> unitsIds = getUnitsIds(getAll(Unit.class));

        if (employeesIds.isEmpty()) {
            model.addAttribute(MESSAGE_KEY, "No employees");
            return "index";
        }
        if (unitsIds.isEmpty()) {
            model.addAttribute(MESSAGE_KEY, "No units");
            return "index";
        }

        model.addAttribute("employeesIds", employeesIds);
        model.addAttribute("unitsIds", unitsIds);
        return "employee/employee.unit";
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST, path = "addToUnit")
    public String addToUnit(Model model, @RequestParam Long employeeId, @RequestParam Long unitId) {
        Employee employee = entityManager.find(Employee.class, employeeId);
        Unit unit = entityManager.find(Unit.class, unitId);

        unit.getEmployees().add(employee);
        entityManager.merge(unit);

        model.addAttribute(MESSAGE_KEY, "Employee has been added to unit");
        return "index";
    }

    /*   A S S I G N   F O R   P R O J E C T   */

    @RequestMapping(method = RequestMethod.GET, path = "assignForProject")
    public String getAssignForProjectForm(Model model) {
        List<Long> employeesIds = getEmployeesIds(getAll(Employee.class));
        List<Long> projectsIds = getProjectsIds(getAll(Project.class));

        if (employeesIds.isEmpty()) {
            model.addAttribute(MESSAGE_KEY, "No employees");
            return "index";
        }
        if (projectsIds.isEmpty()) {
            model.addAttribute(MESSAGE_KEY, "No projects");
            return "index";
        }

        model.addAttribute("employeesIds", employeesIds);
        model.addAttribute("projectsIds", projectsIds);
        return "employee/employee.project";
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST, path = "assignForProject")
    public String assignEmployeeForProject(Model model, @RequestParam Long employeeId, @RequestParam Long projectId) {
        Employee employee = entityManager.find(Employee.class, employeeId);
        Project project = entityManager.find(Project.class, projectId);

        if (!employee.getProjects().contains(project)) {
            employee.getProjects().add(project);
            model.addAttribute(MESSAGE_KEY, "Employee has been assigned to project");
            entityManager.merge(employee);
        } else {
            model.addAttribute(MESSAGE_KEY, "Employee already assigned to the project");
        }

        return "index";
    }

    /*   P R I V A T E   S E C T I O N   */

    private  <T> List<T> getAll(Class<T> clazz) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
        Root<T> rootEntry = criteriaQuery.from(clazz);
        CriteriaQuery<T> all = criteriaQuery.select(rootEntry);
        TypedQuery<T> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }

    private List<Long> getEmployeesIds(List<Employee> entries) {
        List<Long> ids = new ArrayList<>();
        for (Employee entry: entries) {
            ids.add(entry.getId());
        }
        return ids;
    }

    private List<Long> getUnitsIds(List<Unit> entries) {
        List<Long> ids = new ArrayList<>();
        for (Unit entry: entries) {
            ids.add(entry.getId());
        }
        return ids;
    }

    private List<Long> getProjectsIds(List<Project> entries) {
        List<Long> ids = new ArrayList<>();
        for (Project entry: entries) {
            ids.add(entry.getId());
        }
        return ids;
    }

}
