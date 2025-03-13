package com.example.ecs.components;

import org.joml.Vector3f;

public class CameraComponent extends GameComponent {
    public Vector3f position;
    public Vector3f rotation;

    public CameraComponent(Vector3f position, Vector3f rotation) {
        this.position = position;
        this.rotation = rotation;
    }
}