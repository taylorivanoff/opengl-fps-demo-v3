package com.example.factories;

import org.joml.Vector3f;

import com.example.ecs.Entity;
import com.example.ecs.EntityManager;
import com.example.ecs.components.*;
import com.example.rendering.Mesh;
import com.example.rendering.MeshFactory;
import com.example.rendering.ShaderProgram;
import com.example.rendering.loaders.ColladaLoader;

public class EntityFactory {
    private EntityManager entityManager;

    public EntityFactory(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Entity createPlayer(Vector3f position, ShaderProgram shader) {
        Entity player = entityManager.createEntity();

        player.addComponent(new PlayerComponent());
        player.addComponent(new TransformComponent(position.x, position.y, position.z));
        player.addComponent(new VelocityComponent(0, 0, 0));
        player.addComponent(new CameraComponent(70f, 0.1f, 1000f));

        return player;
    }

    public Entity createCube(Vector3f position, float size, ShaderProgram shader) {
        Entity entity = entityManager.createEntity();

        Mesh mesh = MeshFactory.createCube(size);

        entity.addComponent(new TransformComponent(position.x, position.y, position.z));
        entity.addComponent(new RenderableComponent(mesh, shader));
        entity.addComponent(new VelocityComponent(0, 0, 0));

        return entity;
    }

    public Entity createGameObject(String daeFilePath, Vector3f position, ShaderProgram shader) {
        Entity entity = entityManager.createEntity();

        Mesh mesh = ColladaLoader.loadCollada(daeFilePath);

        entity.addComponent(new TransformComponent(position.x, position.y, position.z));
        entity.addComponent(new RenderableComponent(mesh, shader));
        entity.addComponent(new VelocityComponent(0, 0, 0));

        return entity;
    }
}
