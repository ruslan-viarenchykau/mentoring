package com.ruslan.mentoring.jpa.controllers;

import com.ruslan.mentoring.jpa.services.EmployeeManagerService;
import com.ruslan.mentoring.jpa.models.Employee;
import com.ruslan.mentoring.jpa.models.Project;
import com.ruslan.mentoring.jpa.models.Unit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employee")
public class EmployeeController extends AbstractController {
    private static final String ENTITY_KEY = "employee";

    @Autowired
    private EmployeeManagerService service;

    /*   F I N D   */

    @RequestMapping(method = RequestMethod.GET, path = "read")
    public String find(Model model, @RequestParam Long id) {
        Employee employee = service.find(Employee.class, id);
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

    @RequestMapping(method = RequestMethod.POST, path = "create")
    public String create(Model model, @ModelAttribute Employee employee) {
        service.create(employee);
        model.addAttribute(MESSAGE_KEY, "Employee created");
        return "index";
    }

    /*   U P D A T E   */

    @RequestMapping(method = RequestMethod.GET, path = "update")
    public String getUpdateForm(Model model, @RequestParam Long id) {
        Employee employee = service.find(Employee.class, id);
        if (employee == null) {
            model.addAttribute(MESSAGE_KEY, "Employee not found");
            return "index";
        } else {
            model.addAttribute(ENTITY_KEY, employee);
            return "employee/employee.update";
        }
    }

    @RequestMapping(method = RequestMethod.POST, path = "update")
    public String update(Model model, @ModelAttribute Employee employee) {
        boolean updated = service.update(employee);
        model.addAttribute(MESSAGE_KEY, updated ? "Employee updated" : "Employee not found");
        return "index";
    }

    /*   D E L E T E   */

    @RequestMapping(method = RequestMethod.POST, path = "delete")
    public String delete(Model model, @RequestParam Long id) {
        boolean deleted = service.delete(Employee.class, id);
        model.addAttribute(MESSAGE_KEY, deleted ? "Employee deleted" : "Employee not found");
        return "index";
    }

    /*   A D D   T O   U N I T   */

    @RequestMapping(method = RequestMethod.GET, path = "addToUnit")
    public String getAddToUnitForm(Model model) {
        List<Long> employeesIds = service.getIds(Employee.class);
        List<Long> unitsIds = service.getIds(Unit.class);

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

    @RequestMapping(method = RequestMethod.POST, path = "addToUnit")
    public String addToUnit(Model model, @RequestParam Long employeeId, @RequestParam Long unitId) {
        service.addToUnit(employeeId, unitId);

        model.addAttribute(MESSAGE_KEY, "Employee has been added to unit");
        return "index";
    }

    /*   A S S I G N   F O R   P R O J E C T   */

    @RequestMapping(method = RequestMethod.GET, path = "assignForProject")
    public String getAssignForProjectForm(Model model) {
        List<Long> employeesIds = service.getIds(Employee.class);
        List<Long> projectsIds = service.getIds(Project.class);

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

    @RequestMapping(method = RequestMethod.POST, path = "assignForProject")
    public String assignEmployeeForProject(Model model, @RequestParam Long employeeId, @RequestParam Long projectId) {
        boolean assigned = service.assignEmployeeForProject(employeeId, projectId);

        model.addAttribute(MESSAGE_KEY, assigned ? "Employee has been assigned to project"
                : "Employee already assigned to the project or employee/project does not exists");
        return "index";
    }
}
