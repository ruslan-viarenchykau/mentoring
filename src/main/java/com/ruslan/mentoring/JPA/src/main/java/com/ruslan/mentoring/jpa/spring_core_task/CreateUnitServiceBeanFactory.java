package com.ruslan.mentoring.jpa.spring_core_task;

import com.ruslan.mentoring.jpa.spring_core_task.services.CreateUnitService;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component
public class CreateUnitServiceBeanFactory implements FactoryBean<CreateUnitService> {
    @Override
    public CreateUnitService getObject() throws Exception {
        return new CreateUnitService("1", "2", "3");
    }

    @Override
    public Class<?> getObjectType() {
        return CreateUnitService.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
