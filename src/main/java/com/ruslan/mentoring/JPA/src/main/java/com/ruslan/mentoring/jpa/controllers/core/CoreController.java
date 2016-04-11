package com.ruslan.mentoring.jpa.controllers.core;

import com.ruslan.mentoring.jpa.models.Employee;
import com.ruslan.mentoring.jpa.models.Project;
import com.ruslan.mentoring.jpa.models.Unit;
import com.ruslan.mentoring.jpa.services.CreateEmployeeService;
import com.ruslan.mentoring.jpa.services.CreateProjectService;
import com.ruslan.mentoring.jpa.services.CreateUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CoreController {
    @Autowired
    private CreateEmployeeService createEmployeeService;

    private CreateProjectService createProjectService;

    @Autowired
    @Qualifier("defaultCreateUnitService")
    private CreateUnitService createDefaultUnitService;

    @Autowired
    private CreateUnitServiceBeanFactory beanFactory;

    @Autowired
    @Qualifier("customCreateUnitService")
    private CreateUnitService createCustomUnitService;

    @Autowired
    @Qualifier("customCreateUnitService")
    private CreateUnitService createCustomUnitService2;

    @Autowired
    public CoreController(CreateProjectService createProjectService) {
        this.createProjectService = createProjectService;
    }

    @Autowired
    public void setCreateProjectService(CreateProjectService createProjectService) {
        this.createProjectService = createProjectService;
    }

    public Employee getEmployee() {
        return createEmployeeService.createEmployee();
    }

    public Project getProject() {
        return createProjectService.createProject();
    }

    public Unit getDefaultUnit() {
        return createDefaultUnitService.createUnit();
    }

    @RequestMapping(path = "/core")
    public String coreController(Model model) throws Exception {
        model.addAttribute("employee", createEmployeeService.createEmployee());
        model.addAttribute("project", createProjectService.createProject());
        model.addAttribute("defaultUnit", createDefaultUnitService.createUnit());
        model.addAttribute("customUnit", createCustomUnitService.createUnit());;
        model.addAttribute("customUnitBF", beanFactory.getObject().createUnit());
        return "core/core";
    }
}
