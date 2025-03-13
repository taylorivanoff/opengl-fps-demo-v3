package com.example.systems;

import org.joml.Vector3f;

import com.example.ecs.Entity;
import com.example.ecs.components.TransformComponent;
import com.example.input.InputBindingManager;
import com.example.input.InputManager;

public class CharacterControllerSystem extends GameSystem {
    private Entity player;
    private InputManager input;
    private TransformComponent transform;

    // Movement
    private float baseSpeed = 5.0f;
    private float currentSpeed = 5.0f;
    private float sprintMultiplier = 1.8f;
    private float crouchMultiplier = 0.5f;
    private float mouseSensitivity = 0.1f;
    private Vector3f velocity = new Vector3f(0, 0, 0);

    // Jump/gravity parameters
    private float verticalVelocity = 0.0f;
    private float gravity = -15.0f;
    private float jumpForce = 7.0f;
    private boolean isGrounded = true;
    private float crouchYOffset = -0.5f;
    private float normalYPosition = 0.0f;

    // Head bob parameters
    private float headBobTimer = 0.0f;
    private Vector3f headBobOffset = new Vector3f();
    private float headBobSpeed = 3.0f;
    private float headBobVerticalAmount = 0.005f;
    private float headBobHorizontalAmount = 0.003f;
    private boolean isActivelyMoving = false; // New movement state tracker

    public CharacterControllerSystem(Entity playerEntity, InputManager input) {
        this.player = playerEntity;
        this.input = input;
    }

    @Override
    public void update(float dt) {
        TransformComponent transform = player.getComponent(TransformComponent.class);
        if (transform == null) {
            return;
        }

        this.transform = transform;

        handleKeyboard(dt);
        applyGravity(dt);
        updateHeadBob(dt);
    }

    private void handleKeyboard(float dt) {
        isActivelyMoving = input.isActionPressed(InputBindingManager.Action.MOVE_FORWARD) ||
                input.isActionPressed(InputBindingManager.Action.MOVE_BACKWARD) ||
                input.isActionPressed(InputBindingManager.Action.MOVE_LEFT) ||
                input.isActionPressed(InputBindingManager.Action.MOVE_RIGHT);

        // Update speed modifiers
        currentSpeed = baseSpeed;
        if (isGrounded) {
            if (input.isActionPressed(InputBindingManager.Action.SPRINT))
                currentSpeed *= sprintMultiplier;
            if (input.isActionPressed(InputBindingManager.Action.CROUCH)) {
                currentSpeed *= crouchMultiplier;
                normalYPosition = crouchYOffset;
            } else {
                normalYPosition = 0.0f;
            }
        }

        // Calculate movement direction
        Vector3f movement = new Vector3f();
        if (isActivelyMoving) {
            Vector3f forward = calculateForwardVector();
            Vector3f right = calculateRightVector(forward);
            Vector3f horizontalForward = new Vector3f(forward.x, 0, forward.z).normalize();

            if (input.isActionPressed(InputBindingManager.Action.MOVE_FORWARD))
                movement.add(horizontalForward);
            if (input.isActionPressed(InputBindingManager.Action.MOVE_BACKWARD))
                movement.sub(horizontalForward);
            if (input.isActionPressed(InputBindingManager.Action.MOVE_LEFT))
                movement.sub(right);
            if (input.isActionPressed(InputBindingManager.Action.MOVE_RIGHT))
                movement.add(right);

            if (movement.lengthSquared() > 0) {
                movement.normalize().mul(currentSpeed);
            }
        }

        // Apply movement
        velocity.set(movement);
        transform.position.add(velocity.mul(dt));

        // Handle jumping
        if (input.isActionPressed(InputBindingManager.Action.JUMP) && isGrounded) {
            verticalVelocity = jumpForce;
            isGrounded = false;
        }
    }

    private void applyGravity(float dt) {
        if (!isGrounded) {
            verticalVelocity += gravity * dt;
            transform.position.add(velocity.mul(dt)); // Maintain horizontal velocity
        }

        transform.position.y += verticalVelocity * dt;

        if (transform.position.y <= normalYPosition) {
            transform.position.y = normalYPosition;
            verticalVelocity = 0.0f;
            isGrounded = true;
            velocity.set(0, 0, 0); // Reset velocity when landing
        }
    }

    private void updateHeadBob(float dt) {
        boolean shouldBob = isGrounded && isActivelyMoving;

        if (shouldBob) {
            headBobTimer += dt * headBobSpeed;
            float verticalOffset = (float) Math.sin(headBobTimer) * headBobVerticalAmount;
            float horizontalOffset = (float) Math.cos(headBobTimer * 2) * headBobHorizontalAmount;

            Vector3f forward = calculateForwardVector();
            Vector3f right = calculateRightVector(forward);
            Vector3f up = new Vector3f(0, 1, 0);

            // Calculate offset in camera's local space
            Vector3f localOffset = new Vector3f()
                    .add(right.mul(horizontalOffset))
                    .add(up.mul(verticalOffset));

            headBobOffset.set(localOffset);
        } else {
            // Smoothly return to neutral position
            headBobOffset.lerp(new Vector3f(0, 0, 0), 5f * dt);
            if (headBobOffset.lengthSquared() < 1e-6f) {
                headBobOffset.set(0, 0, 0);
            }
            headBobTimer = 0;
        }
    }

    private Vector3f calculateForwardVector() {
        Vector3f forward = new Vector3f();

        float yaw = (float) Math.toRadians(transform.rotation.y);
        float pitch = (float) Math.toRadians(transform.rotation.x);

        forward.x = (float) (Math.cos(yaw) * Math.cos(pitch));
        forward.y = (float) Math.sin(pitch);
        forward.z = (float) (Math.sin(yaw) * Math.cos(pitch));

        return forward.normalize();
    }

    private Vector3f calculateRightVector(Vector3f forward) {
        Vector3f right = new Vector3f();
        Vector3f up = new Vector3f(0, 1, 0);

        forward.cross(up, right);
        return right.normalize();
    }
}