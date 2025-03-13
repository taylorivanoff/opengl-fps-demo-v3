package com.example.ecs.components;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import com.example.core.GameConstants;

public class CameraComponent extends GameComponent {
    public Vector3f position;
    public Vector3f rotation;

    private float fov = 70.0f;
    private float aspectRatio = GameConstants.WINDOW_WIDTH / GameConstants.WINDOW_HEIGHT;
    private float nearPlane = 0.1f;
    private float farPlane = 1000f;

    public CameraComponent(Vector3f position, Vector3f rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public Matrix4f getProjectionMatrix() {
        return new Matrix4f().perspective((float) Math.toRadians(fov), (float) aspectRatio, nearPlane, farPlane);
    }
}