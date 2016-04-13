package com.ruslan.mentoring.jpa.spring_core_task.services;

import com.ruslan.mentoring.jpa.models.Unit;
import org.springframework.stereotype.Service;

@Service
public class CreateUnitService {
    private String unitName;
    private String unitTitle;
    private String unitDescription;

    public CreateUnitService() {
    }

    public CreateUnitService(String unitName, String unitTitle, String unitDescription) {
        this.unitName = unitName;
        this.unitTitle = unitTitle;
        this.unitDescription = unitDescription;
    }

    public Unit createUnit() {
        Unit unit = new Unit();
        unit.setName(unitName);
        unit.setTitle(unitTitle);
        unit.setDescription(unitDescription);
        return unit;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public void setUnitTitle(String unitTitle) {
        this.unitTitle = unitTitle;
    }

    public void setUnitDescription(String unitDescription) {
        this.unitDescription = unitDescription;
    }
}
