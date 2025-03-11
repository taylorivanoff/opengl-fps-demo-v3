package com.example.ecs.components;

import com.example.ecs.Component;

public class TransformComponent extends Component {
    public float x, y, z;
    public float rotationX, rotationY, rotationZ;
    public float scaleX = 1.0f, scaleY = 1.0f, scaleZ = 1.0f;

    public TransformComponent(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
