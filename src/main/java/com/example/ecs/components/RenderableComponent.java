package com.example.ecs.components;

import com.example.rendering.Mesh;
import com.example.rendering.Shader;

public class RenderableComponent extends GameComponent {
    public Mesh mesh;
    public Shader shader;

    public RenderableComponent(Mesh mesh, Shader shader) {
        this.mesh = mesh;
        this.shader = shader;
    }
}