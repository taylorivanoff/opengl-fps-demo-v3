package com.example.ecs.components;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import com.example.ecs.Component;

public class Transform extends Component {
    public Vector3f position;
    public Quaternionf rotation;
    public Vector3f scale;

    public Transform() {
        this.position = new Vector3f(0, 0, 0);
        this.rotation = new Quaternionf();
        this.scale = new Vector3f(1, 1, 1);
    }

    public Transform(Vector3f position) {
        this.position = position;
        this.rotation = new Quaternionf();
        this.scale = new Vector3f(1, 1, 1);
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

    public Matrix4f getMatrix() {
        Matrix4f matrix = new Matrix4f().identity();
        matrix.translate(position);
        matrix.rotate(rotation);
        matrix.scale(scale);
        return matrix;
    }
}
