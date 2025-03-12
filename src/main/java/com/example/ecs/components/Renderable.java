package com.example.ecs.components;

import com.example.ecs.Component;
import com.example.rendering.Mesh;
import com.example.rendering.ShaderProgram;

public class Renderable extends Component {
    public Mesh mesh;
    public ShaderProgram shader;

    public Renderable(Mesh mesh, ShaderProgram shader) {
        this.mesh = mesh;
        this.shader = shader;
    }
}