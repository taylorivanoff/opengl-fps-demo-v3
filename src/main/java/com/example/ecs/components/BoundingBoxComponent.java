package com.example.ecs.components;

import com.example.ecs.Component;

public class BoundingBoxComponent extends Component {
    public float width;
    public float height;
    public float depth;

    public BoundingBoxComponent(float width, float height, float depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;
    }
}
