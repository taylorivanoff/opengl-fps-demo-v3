package com.example.ecs.components;

import com.example.rendering.Mesh;
import com.example.rendering.Shader;

public class Renderable extends Component {
    public Mesh mesh;
    public Shader shader;

    public Renderable(Mesh mesh, Shader shader) {
        this.mesh = mesh;
        this.shader = shader;
    }
}