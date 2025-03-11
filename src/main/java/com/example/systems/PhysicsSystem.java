package com.example.systems;

import com.example.ecs.Entity;
import com.example.ecs.EntityManager;
import com.example.ecs.GameSystem;
import com.example.ecs.components.TransformComponent;
import com.example.ecs.components.VelocityComponent;

public class PhysicsSystem extends GameSystem {
    private EntityManager entityManager;

    public PhysicsSystem(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void update(float deltaTime) {
        for (Entity entity : entityManager.getAllEntities()) {
            TransformComponent transform = entity.getComponent(TransformComponent.class);
            VelocityComponent velocity = entity.getComponent(VelocityComponent.class);
            if (transform != null && velocity != null) {
                transform.x += velocity.vx * deltaTime;
                transform.y += velocity.vy * deltaTime;
                transform.z += velocity.vz * deltaTime;
            }
        }
        // System.out.println("Physics updated for deltaTime: " + deltaTime);
    }
}
