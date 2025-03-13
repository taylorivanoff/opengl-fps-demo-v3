package com.example.ecs;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import com.example.ecs.components.Component;

public class Entity {
    private final int id;
    private final Map<Class<? extends Component>, Component> components = new ConcurrentHashMap<>();

    public Entity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public <T extends Component> void addComponent(T component) {
        components.put(component.getClass(), component);
    }

    public <T extends Component> T getComponent(Class<T> componentClass) {
        return componentClass.cast(components.get(componentClass));
    }

    public Collection<Component> getAllComponents() {
        return Collections.unmodifiableCollection(components.values());
    }

    public boolean hasComponent(Class<? extends Component> componentClass) {
        return components.containsKey(componentClass);
    }
}