package com.ruslan.mentoring.jpa.services;

import com.ruslan.mentoring.jpa.models.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Controller
@RequestMapping("/employees")
public class EmployeeService extends AbstractService {
    private static final String ENTITY_KEY = "employee";

    @PersistenceContext
    private EntityManager entityManager;

    @RequestMapping(method = RequestMethod.GET, path = "create")
    public String getFormCreate(Model model, Employee employee) {
        model.addAttribute(MESSAGE_KEY, "Create employee");
        return "employee/employee.form";
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public String create(Model model, @ModelAttribute Employee employee) {
        entityManager.persist(employee);
        model.addAttribute(ENTITY_KEY, employee);
        model.addAttribute(MESSAGE_KEY, "created");
        return "employee/result";
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST, path = "update")
    public String update(Model model, @ModelAttribute Employee employee) {
        entityManager.merge(employee);
        model.addAttribute(ENTITY_KEY, employee);
        model.addAttribute(MESSAGE_KEY, "updated");
        return "employee/result";
    }

    @RequestMapping(method = RequestMethod.GET, value = "{id}")
    public String find(Model model, @PathVariable Long id) {
        Employee employee = entityManager.find(Employee.class, id);
        if (employee == null) {
            Employee stub = new Employee();
            stub.setId(id);
            model.addAttribute(ENTITY_KEY, stub);
            model.addAttribute(MESSAGE_KEY, "not found");
        } else {
            model.addAttribute(ENTITY_KEY, employee);
            model.addAttribute(MESSAGE_KEY, "found");
        }
        return "employee/result";
    }

    @RequestMapping(method = RequestMethod.GET, path = "update")
    public String getFormUpdate(Model model, Employee employee, @RequestParam Long id) {
        employee = entityManager.find(Employee.class, id);
        if (employee == null) {
            Employee stub = new Employee();
            stub.setId(id);
            model.addAttribute(ENTITY_KEY, stub);
        } else {
            model.addAttribute(ENTITY_KEY, employee);
        }
        model.addAttribute(MESSAGE_KEY, "Update employee");
        return "employee/employee.update";
    }

    @Transactional
    @RequestMapping(method = RequestMethod.GET, path = "delete")
    public String delete(Model model, @RequestParam Long id) {
        Employee employee = entityManager.find(Employee.class, id);
        if (employee != null) {
            entityManager.remove(employee);
        }
        return "redirect:/";
    }
}
