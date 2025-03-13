package com.example.factories;

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

    public Entity addCamera(Vector3f camPos, Vector3f camRotation) {
        Entity camera = em.createEntity();
        camera.addComponent(new CameraComponent(camPos, camRotation));
        return camera;
    }

    public Entity addPlayer(Vector3f position, Shader shader) {
        Entity player = em.createEntity();
        player.addComponent(new PlayerComponent());
        player.addComponent(new CharacterControllerComponent());
        player.addComponent(new TransformComponent(position));
        return player;
    }

    public Entity addGameObject(Mesh mesh, Vector3f position, Shader shader) {
        Entity entity = em.createEntity();
        entity.addComponent(new TransformComponent(position));
        entity.addComponent(new RenderableComponent(mesh, shader));
        return entity;
    }
}
