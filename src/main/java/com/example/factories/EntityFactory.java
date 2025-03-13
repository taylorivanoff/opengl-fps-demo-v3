package com.example.factories;

import org.joml.Quaternionf;
import org.joml.Vector3f;

import com.example.ecs.Entity;
import com.example.ecs.EntityManager;
import com.example.ecs.components.*;
import com.example.rendering.Mesh;
import com.example.rendering.Shader;

public class EntityFactory {
    private EntityManager em;

    public EntityFactory(EntityManager entityManager) {
        this.em = entityManager;
    }

    public Entity addCamera() {
        Entity e = em.createEntity();
        e.addComponent(new Transform());
        e.addComponent(new Camera());
        return e;
    }

    public Entity addPlayer(Vector3f position, Quaternionf rotation, Vector3f scale) {
        Entity e = em.createEntity();
        e.addComponent(new Transform(position,
                rotation,
                scale));
        e.addComponent(new Player());
        e.addComponent(new CharacterController());
        return e;
    }

    public Entity addGameObject(Mesh mesh, Transform transform, Shader shader) {
        Entity e = em.createEntity();
        e.addComponent(transform);
        e.addComponent(new Renderable(mesh, shader));
        return e;
    }
}
