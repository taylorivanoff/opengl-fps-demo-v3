package com.example.systems;

import org.joml.Vector3f;

import com.example.ecs.Entity;
import com.example.ecs.components.TransformComponent;
import com.example.input.InputBindingManager;
import com.example.input.InputManager;
import com.example.utils.Logger;
import com.example.utils.Logger.Level;

public class CharacterControllerSystem extends GameSystem {
    private Entity playerEntity;
    private InputManager inputManager;

    private float speed = 5.0f;
    private float crouchSpeedModifier = 0.5f;
    private float jumpForce = 5.0f;
    private float gravity = -9.8f;
    private boolean isGrounded = true;

    public CharacterControllerSystem(Entity playerEntity, InputManager inputManager) {
        this.playerEntity = playerEntity;
        this.inputManager = inputManager;
    }

    @Override
    public void update(float deltaTime) {
        TransformComponent transform = playerEntity.getComponent(TransformComponent.class);
        if (transform == null)
            return;

        Vector3f velocity = new Vector3f();

        // 🔹 Compute Movement Directions
        Vector3f forward = new Vector3f(
                (float) Math.sin(Math.toRadians(transform.rotation.y)),
                0,
                (float) -Math.cos(Math.toRadians(transform.rotation.y)));

        Vector3f right = new Vector3f(forward.z, 0, -forward.x);

        // 🔹 Crouch Modifier
        float appliedSpeed = speed;
        if (inputManager.isActionPressed(InputBindingManager.Action.CROUCH)) {
            appliedSpeed *= crouchSpeedModifier;
        }

        // 🔹 Movement Input (WASD)
        if (inputManager.isActionPressed(InputBindingManager.Action.MOVE_FORWARD))
            velocity.add(forward.mul(appliedSpeed * deltaTime));
        if (inputManager.isActionPressed(InputBindingManager.Action.MOVE_BACKWARD))
            velocity.sub(forward.mul(appliedSpeed * deltaTime));
        if (inputManager.isActionPressed(InputBindingManager.Action.MOVE_LEFT))
            velocity.sub(right.mul(appliedSpeed * deltaTime));
        if (inputManager.isActionPressed(InputBindingManager.Action.MOVE_RIGHT))
            velocity.add(right.mul(appliedSpeed * deltaTime));

        // 🔹 Gravity & Jumping
        if (isGrounded && inputManager.isActionPressed(InputBindingManager.Action.JUMP)) {
            transform.velocity.y = jumpForce;
            isGrounded = false;
        }

        transform.velocity.y += gravity * deltaTime; // Apply gravity
        velocity.add(transform.velocity.mul(deltaTime));

        // 🔹 Simulate Ground Collision (Example)
        if (transform.position.y <= 1.0f) {
            transform.position.y = 1.0f;
            transform.velocity.y = 0;
            isGrounded = true;
        }

        // 🔹 Apply Movement
        transform.position.add(velocity);

        // 🔹 Debug Output
        Logger.log(Level.DEBUG, "player: " + transform.position);
    }
}