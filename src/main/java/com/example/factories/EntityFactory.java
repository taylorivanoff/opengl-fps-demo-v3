package com.example.factories;

import org.joml.Vector3f;

import com.example.ecs.Entity;
import com.example.ecs.EntityManager;
import com.example.ecs.components.Camera;
import com.example.ecs.components.Player;
import com.example.ecs.components.Renderable;
import com.example.ecs.components.Transform;
import com.example.rendering.Mesh;
import com.example.rendering.MeshFactory;
import com.example.rendering.ShaderProgram;
import com.example.rendering.loaders.ColladaLoader;

public class EntityFactory {
    private EntityManager em;

    public EntityFactory(EntityManager entityManager) {
        this.em = entityManager;
    }

    public Entity createPlayer(Vector3f position, ShaderProgram shader) {
        Entity player = em.createEntity();
        player.addComponent(new Player());
        player.addComponent(new Transform(position));
        player.addComponent(new Camera());

        return player;
    }

    public Entity createCube(Vector3f position, float size, ShaderProgram shader) {
        Entity entity = em.createEntity();

        Mesh mesh = MeshFactory.createCube(size);

        entity.addComponent(new Transform(position));
        entity.addComponent(new Renderable(mesh, shader));

        return entity;
    }

    public Entity createGameObject(String daeFilePath, Vector3f position, ShaderProgram shader) {
        Entity entity = em.createEntity();

        Mesh mesh = ColladaLoader.loadCollada(daeFilePath);

        entity.addComponent(new Transform(position));
        entity.addComponent(new Renderable(mesh, shader));

        return entity;
    }
}
