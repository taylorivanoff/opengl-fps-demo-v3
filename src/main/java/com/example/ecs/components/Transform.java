package com.example.ecs.components;

import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Transform extends Component {
    public Vector3f position;
    public Quaternionf rotation;
    public Vector3f scale;

    public Transform(Vector3f position, Quaternionf rotation, Vector3f scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    public Transform() {
        this.position = new Vector3f();
        this.rotation = new Quaternionf();
        this.scale = new Vector3f(1);
    }
}
