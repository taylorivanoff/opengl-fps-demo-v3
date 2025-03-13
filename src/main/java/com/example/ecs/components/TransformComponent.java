package com.example.ecs.components;

import org.joml.Quaternionf;
import org.joml.Vector3f;

public class TransformComponent extends GameComponent {
    public Vector3f position;
    public Quaternionf rotation;
    public Vector3f scale;
    public Vector3f velocity;

    public TransformComponent() {
        this.position = new Vector3f(0, 0, 0);
        this.rotation = new Quaternionf();
        this.scale = new Vector3f(1, 1, 1);
        this.velocity = new Vector3f(0, 0, 0);
    }

    public TransformComponent(Vector3f position) {
        this.position = position;
        this.rotation = new Quaternionf();
        this.scale = new Vector3f(1, 1, 1);
        this.velocity = new Vector3f(0, 0, 0);
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Quaternionf getRotation() {
        return rotation;
    }

    public void setRotation(Quaternionf rotation) {
        this.rotation = rotation;
    }

    public Vector3f getScale() {
        return scale;
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
    }

    public Vector3f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector3f velocity) {
        this.velocity = velocity;
    }

}
