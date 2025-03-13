package com.example.ecs;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import com.example.ecs.components.GameComponent;

public class Entity {
    private final int id;
    private final Map<Class<? extends GameComponent>, GameComponent> components = new ConcurrentHashMap<>();

    public Entity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public <T extends GameComponent> void addComponent(T component) {
        components.put(component.getClass(), component);
    }

    public <T extends GameComponent> T getComponent(Class<T> componentClass) {
        return componentClass.cast(components.get(componentClass));
    }

    public Collection<GameComponent> getAllComponents() {
        return Collections.unmodifiableCollection(components.values());
    }

    public boolean hasComponent(Class<? extends GameComponent> componentClass) {
        return components.containsKey(componentClass);
    }
}