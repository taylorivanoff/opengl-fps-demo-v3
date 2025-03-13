package com.example.ecs.components;

import com.example.ecs.Entity;

public class Parent extends Component {
    private Entity parent;

    public Parent(Entity parent) {
        this.parent = parent;
    }

    public Entity getParent() {
        return parent;
    }

    public void setParent(Entity parent) {
        this.parent = parent;
    }
}