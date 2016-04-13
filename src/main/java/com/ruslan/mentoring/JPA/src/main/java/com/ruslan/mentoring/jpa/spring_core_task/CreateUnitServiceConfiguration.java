package com.ruslan.mentoring.jpa.spring_core_task;

import com.ruslan.mentoring.jpa.spring_core_task.services.CreateUnitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import java.util.Random;

@Configuration
public class CreateUnitServiceConfiguration {
    @Value("${default.unit.name}")
    private String unitName;

    @Value("${default.unit.title}")
    private String unitTitle;

    @Value("${default.unit.description}")
    private String unitDescription;

    @Bean(name = "customCreateUnitService")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public CreateUnitService getCreateUnitService() {
        Random random = new Random();
        CreateUnitService createUnitService = new CreateUnitService();
        createUnitService.setUnitName("Custom Unit" + random.nextInt());
        createUnitService.setUnitDescription("Description" + random.nextInt());
        createUnitService.setUnitTitle("Title" + random.nextInt());
        return createUnitService;
    }

    @Bean(name = "defaultCreateUnitService")
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public CreateUnitService getDefaultCreateUnitService() {
        return new CreateUnitService(unitName, unitTitle, unitDescription);
    }
}
