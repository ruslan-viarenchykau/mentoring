package com.ruslan.mentoring.jpa.controllers;

import com.ruslan.mentoring.jpa.models.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    @RequestMapping(method = RequestMethod.GET, path = "delete")
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
}
