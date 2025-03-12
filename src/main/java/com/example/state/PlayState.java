package com.example.state;

import org.joml.Vector3f;

import com.example.core.WindowManager;
import com.example.factories.EntityFactory;
import com.example.rendering.ShaderProgram;
import com.example.systems.CameraControlSystem;
import com.example.systems.RenderSystem;

public class PlayState extends GameState {
    private final WindowManager windowManager;

    public PlayState(WindowManager windowManager) {
        this.windowManager = windowManager;
    }

    @Override
    public void init() {
        ShaderProgram shader = new ShaderProgram("assets/shaders/default.vert", "assets/shaders/default.frag");

        RenderSystem renderSystem = new RenderSystem(entityManager);
        entityManager.addSystem(renderSystem);

        CameraControlSystem cameraSystem = new CameraControlSystem(windowManager, entityManager);
        entityManager.addSystem(cameraSystem);

        EntityFactory entityFactory = new EntityFactory(entityManager);

        entityFactory.createPlayer(new Vector3f(0, 2, 5), shader);

        float spacing = 5.0f;
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                entityFactory.createGameObject("assets/torus.dae", new Vector3f(x * spacing, 0, z * spacing - 10),
                        shader);
            }
        }
    }

    @Override
    public void cleanup() {
    }
}