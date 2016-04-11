package com.ruslan.mentoring.jpa.controllers;

import com.ruslan.mentoring.jpa.models.Unit;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/unit")
public class UnitController extends AbstractController {
    private static final String ENTITY_KEY = "unit";

    /*   F I N D   */

    @RequestMapping(method = RequestMethod.GET, path = "read")
    public String find(Model model, @RequestParam Long id) {
        Unit unit = entityManager.find(Unit.class, id);
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

    @Transactional
    @RequestMapping(method = RequestMethod.POST, path = "create")
    public String create(Model model, @ModelAttribute Unit unit) {
        entityManager.persist(unit);
        model.addAttribute(MESSAGE_KEY, "Unit created");
        return "index";
    }

    /*   U P D A T E   */

    @RequestMapping(method = RequestMethod.GET, path = "update")
    public String getUpdateForm(Model model, Unit unit, @RequestParam Long id) {
        unit = entityManager.find(Unit.class, id);
        if (unit == null) {
            model.addAttribute(MESSAGE_KEY, "Unit not found");
            return "index";
        } else {
            model.addAttribute(ENTITY_KEY, unit);
            return "unit/unit.update";
        }
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST, path = "update")
    public String update(Model model, @ModelAttribute Unit unit) {
        Unit unitById = entityManager.find(Unit.class, unit.getId());
        if (unitById != null) {
            entityManager.merge(unit);
            model.addAttribute(MESSAGE_KEY, "Unit updated");
        } else {
            model.addAttribute(MESSAGE_KEY, "Unit not found");
        }
        return "index";
    }

    /*   D E L E T E   */

    @Transactional
    @RequestMapping(method = RequestMethod.POST, path = "delete")
    public String delete(Model model, @RequestParam Long id) {
        Unit unit = entityManager.find(Unit.class, id);
        if (unit != null) {
            entityManager.remove(unit);
            model.addAttribute(MESSAGE_KEY, "Unit deleted");
        } else {
            model.addAttribute(MESSAGE_KEY, "Unit not found");
        }
        return "index";
    }
}
