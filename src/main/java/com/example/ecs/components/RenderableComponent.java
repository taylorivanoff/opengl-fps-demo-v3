package com.example.ecs.components;

import com.example.rendering.Mesh;
import com.example.rendering.ShaderProgram;

public class RenderableComponent extends GameComponent {
    public Mesh mesh;
    public ShaderProgram shader;

    public RenderableComponent(Mesh mesh, ShaderProgram shader) {
        this.mesh = mesh;
        this.shader = shader;
    }
}