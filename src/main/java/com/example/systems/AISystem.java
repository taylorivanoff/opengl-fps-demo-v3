package com.example.systems;

import com.example.ecs.Entity;
import com.example.ecs.EntityManager;
import com.example.ecs.GameSystem;
import com.example.ecs.components.TransformComponent;

public class AISystem extends GameSystem {
    private EntityManager entityManager;

    public AISystem(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void update(float deltaTime) {
        for (Entity entity : entityManager.getAllEntities()) {
            TransformComponent transform = entity.getComponent(TransformComponent.class);
            if (transform != null) {
                transform.x += (Math.random() - 0.5f) * deltaTime;
                transform.z += (Math.random() - 0.5f) * deltaTime;
            }
        }
    }
}