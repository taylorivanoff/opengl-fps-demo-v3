package com.example.systems;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

import com.example.ecs.EntityManager;
import com.example.ecs.GameSystem;
import com.example.input.InputManager;

public class WeaponSystem extends GameSystem {
    private EntityManager entityManager;
    private InputManager inputManager;
    private float cooldownTimer = 0.0f;
    private final float COOLDOWN_DURATION = 0.5f; // 0.5 seconds between shots

    public WeaponSystem(EntityManager entityManager, InputManager inputManager) {
        this.entityManager = entityManager;
        this.inputManager = inputManager;
    }

    @Override
    public void update(float deltaTime) {
        cooldownTimer -= deltaTime;
        // Check if fire button (left mouse button) is pressed and cooldown is finished.
        if (inputManager.isKeyDown(GLFW_MOUSE_BUTTON_LEFT) && cooldownTimer <= 0.0f) {
            fireWeapon();
            cooldownTimer = COOLDOWN_DURATION;
        }
    }

    private void fireWeapon() {
        // Spawn a projectile entity.
        // Entity projectile = entityManager.createEntity();
        // Attach components such as Transform, Velocity, etc.
        // For example, initialize the projectile's position and velocity.
        // System.out.println("Firing weapon: spawned projectile with id " +
        // projectile.getId());
    }
}