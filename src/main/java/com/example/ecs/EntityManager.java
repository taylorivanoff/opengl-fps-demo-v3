package com.example.ecs;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.example.systems.GameSystem;

public class EntityManager {
    private final Map<Integer, Entity> entities = new ConcurrentHashMap<>();
    private final List<GameSystem> systems = new ArrayList<>();
    private final AtomicInteger nextId = new AtomicInteger(0);

    public List<GameSystem> getSystems() {
        return Collections.unmodifiableList(systems);
    }

    public Entity createEntity() {
        int id = nextId.getAndIncrement();
        Entity entity = new Entity(id);
        entities.put(id, entity);
        return entity;
    }

    public Entity getEntity(int id) {
        return entities.get(id);
    }

    public Collection<Entity> getAllEntities() {
        return Collections.unmodifiableCollection(entities.values());
    }

    public <T extends Component> List<Entity> getEntitiesWith(Class<T> componentClass) {
        List<Entity> result = new ArrayList<>();
        for (Entity entity : entities.values()) {
            if (entity.hasComponent(componentClass)) {
                result.add(entity);
            }
        }
        return result;
    }

    public void clear() {
        entities.clear();
    }

    public void addSystem(GameSystem system) {
        systems.add(system);
    }

    public void update(float deltaTime) {
        for (GameSystem system : systems) {
            system.update(deltaTime);
        }
    }
}