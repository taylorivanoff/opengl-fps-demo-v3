package com.example.ecs.components;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import com.example.core.GameConstants;
import com.example.ecs.Component;

public class CameraComponent extends Component {
    public float fov;
    public float nearClip;
    public float farClip;

    private float yaw = -90f;
    private float pitch = 0f;
    private float mouseSensitivity = 0.1f;

    private Vector3f position = new Vector3f(0, 0, 0);
    private Vector3f front = new Vector3f(0, 0, -1);
    private Vector3f up = new Vector3f(0, 1, 0);
    private Matrix4f projection;

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public CameraComponent(float fov, float nearClip, float farClip) {
        this.fov = fov;
        this.nearClip = nearClip;
        this.farClip = farClip;

        this.projection = new Matrix4f().perspective(
                (float) Math.toRadians(fov),
                (float) GameConstants.WINDOW_WIDTH / GameConstants.WINDOW_HEIGHT,
                nearClip,
                farClip);

        updateCameraVectors();
    }

    public void processMouseMovement(float xoffset, float yoffset, boolean constrainPitch) {
        xoffset *= mouseSensitivity;
        yoffset *= mouseSensitivity;

        yaw += xoffset;
        pitch += yoffset;

        if (constrainPitch) {
            if (pitch > 89.0f)
                pitch = 89.0f;
            if (pitch < -89.0f)
                pitch = -89.0f;
        }

        updateCameraVectors();
    }

    private void updateCameraVectors() {
        front.x = (float) (Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));
        front.y = (float) Math.sin(Math.toRadians(pitch));
        front.z = (float) (Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));
        front.normalize();
    }

    public Matrix4f getViewMatrix() {
        return new Matrix4f().lookAt(position, new Vector3f(position).add(front), up);
    }

    public Matrix4f getProjectionMatrix() {
        return projection;
    }

    public void setPosition(Vector3f position) {
        this.position.set(position);
    }

    public Vector3f getPosition() {
        return new Vector3f(position);
    }

    public Vector3f getFront() {
        return new Vector3f(front);
    }
}
