package com.dmdev.spring.listener;

import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class EntityListener {

    @EventListener(condition = "#root.args[0].accessType.name() == 'READ'")
    @Order(10)
    public void acceptEntity(EntityEvent entityEvent){
        System.out.println("Entity " + entityEvent);
    }
}
