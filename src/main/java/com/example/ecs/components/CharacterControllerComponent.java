package com.example.ecs.components;

import org.joml.Vector3f;

public class CharacterControllerComponent extends GameComponent {
    public Vector3f velocity = new Vector3f(0, 0, 0);
    public boolean isGrounded = false;
    public boolean isCrouching = false;
    public boolean isJumping = false;
    public float moveSpeed = 5.0f;
    public float crouchSpeed = 2.5f;
    public float jumpForce = 6.0f;
    public float gravity = -9.81f;
    public float headBobAmount = 0.05f;
    public float headBobSpeed = 8.0f;
    public float headBobTimer = 0.0f;

    public CharacterControllerComponent() {
    }
}
