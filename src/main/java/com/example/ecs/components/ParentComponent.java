package com.example.ecs.components;

import com.example.ecs.Entity;

public class ParentComponent extends GameComponent {
    private Entity parent;

    public ParentComponent(Entity parent) {
        this.parent = parent;
    }

    public Entity getParent() {
        return parent;
    }

    public void setParent(Entity parent) {
        this.parent = parent;
    }
}