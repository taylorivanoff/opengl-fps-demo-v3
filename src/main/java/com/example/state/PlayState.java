package com.example.state;

import org.joml.Vector3f;

import com.example.core.WindowManager;
import com.example.ecs.Entity;
import com.example.factories.EntityFactory;
import com.example.input.InputBindingManager;
import com.example.input.InputManager;
import com.example.rendering.Mesh;
import com.example.rendering.ShaderProgram;
import com.example.rendering.loaders.ColladaLoader;
import com.example.systems.*;

public class PlayState extends GameState {
    public PlayState(WindowManager windowManager) {
        super(windowManager);
    }

    @Override
    public void init() {
        Mesh torus = ColladaLoader.loadCollada("assets/torus.dae");
        ShaderProgram shader = new ShaderProgram("assets/shaders/default.vert", "assets/shaders/default.frag");

        InputBindingManager bindingManager = new InputBindingManager();
        InputManager inputManager = new InputManager(windowManager.getWindowHandle(), bindingManager);

        EntityFactory scene = new EntityFactory(ecs);

        Entity player = scene.addPlayer(new Vector3f());
        Entity camera = scene.addCamera(new Vector3f(), new Vector3f());

        float spacing = 5.0f;
        int count = 5;
        for (int x = -count; x <= count; x++) {
            for (int z = -count; z <= count; z++) {
                scene.addGameObject(torus, new Vector3f(x * spacing, 0, z * spacing - 10),
                        shader);
            }
        }

        CharacterControllerSystem characterControllerSystem = new CharacterControllerSystem(player, inputManager);
        CameraSystem cameraSystem = new CameraSystem(camera, player, inputManager);
        RenderSystem renderSystem = new RenderSystem(camera, ecs);

        ecs.addSystem(characterControllerSystem);
        ecs.addSystem(cameraSystem);
        ecs.addSystem(renderSystem);

    }

    @Override
    public void cleanup() {
        //
    }
}