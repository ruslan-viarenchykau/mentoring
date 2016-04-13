package com.ruslan.mentoring.jpa.controllers;

import com.ruslan.mentoring.jpa.services.EntityManagerService;
import com.ruslan.mentoring.jpa.models.Unit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/unit")
public class UnitController extends AbstractController {
    private static final String ENTITY_KEY = "unit";

    @Autowired
    private EntityManagerService service;

    /*   F I N D   */

    @RequestMapping(method = RequestMethod.GET, path = "read")
    public String find(Model model, @RequestParam Long id) {
        Unit unit = service.find(Unit.class, id);
        if (unit == null) {
            model.addAttribute(MESSAGE_KEY, "Unit not found");
            return "index";
        } else {
            model.addAttribute(ENTITY_KEY, unit);
            return "unit/unit.view";
        }
    }

    /*   C R E A T E   */

    @RequestMapping(method = RequestMethod.GET, path = "create")
    public String getCreateForm(Model model, Unit unit) {
        return "unit/unit.create";
    }

    @RequestMapping(method = RequestMethod.POST, path = "create")
    public String create(Model model, @ModelAttribute Unit unit) {
        service.create(unit);
        model.addAttribute(MESSAGE_KEY, "Unit created");
        return "index";
    }

    /*   U P D A T E   */

    @RequestMapping(method = RequestMethod.GET, path = "update")
    public String getUpdateForm(Model model, @RequestParam Long id) {
        Unit unit = service.find(Unit.class, id);
        if (unit == null) {
            model.addAttribute(MESSAGE_KEY, "Unit not found");
            return "index";
        } else {
            model.addAttribute(ENTITY_KEY, unit);
            return "unit/unit.update";
        }
    }

    @RequestMapping(method = RequestMethod.POST, path = "update")
    public String update(Model model, @ModelAttribute Unit unit) {
        boolean updated = service.update(unit);
        model.addAttribute(MESSAGE_KEY, updated ? "Unit updated" : "Unit not found");
        return "index";
    }

    /*   D E L E T E   */

    @RequestMapping(method = RequestMethod.POST, path = "delete")
    public String delete(Model model, @RequestParam Long id) {
        boolean deleted = service.delete(Unit.class, id);
        model.addAttribute(MESSAGE_KEY, deleted ? "Unit deleted" : "Unit not found");
        return "index";
    }
}
