package com.example.state;

import java.util.Map;

import org.joml.Quaternionf;
import org.joml.Vector3f;

import com.example.core.WindowManager;
import com.example.ecs.Entity;
import com.example.ecs.components.Transform;
import com.example.factories.EntityFactory;
import com.example.input.InputBindingManager;
import com.example.input.InputManager;
import com.example.rendering.Mesh;
import com.example.rendering.Shader;
import com.example.rendering.loaders.ColladaSceneLoader;
import com.example.systems.*;
import com.example.utils.Logger;
import com.example.utils.Logger.Level;

public class PlayState extends GameState {
    public PlayState(WindowManager windowManager) {
        super(windowManager);
    }

    @Override
    public void init() {
        Shader shader = new Shader("assets/shaders/default.vert", "assets/shaders/default.frag");

        InputBindingManager bindingManager = new InputBindingManager();
        InputManager inputManager = new InputManager(windowManager.getWindowHandle(), bindingManager);

        EntityFactory scene = new EntityFactory(ecs);

        Entity player = scene.addPlayer(new Vector3f(0, 2, 0), new Quaternionf(), new Vector3f());
        Entity camera = scene.addCamera();

        // Mesh torus = ColladaLoader.load("assets/Torus.dae");
        // Mesh cube = ColladaLoader.load("assets/Cube.dae");

        Map<Mesh, Transform> environmentMeshes = ColladaSceneLoader.load("assets/new-scene.dae");
        for (Map.Entry<Mesh, Transform> entry : environmentMeshes.entrySet()) {
            Mesh mesh = entry.getKey();
            Transform transform = entry.getValue();
            System.out.println(transform.scale);

            scene.addGameObject(mesh, transform, shader);
            Logger.log(Level.INFO, "Created entity at position: " + transform.position);
        }

        // float spacing = 5.0f;
        // int count = 5;
        // for (int x = -count; x <= count; x++) {
        // for (int z = -count; z <= count; z++) {
        // scene.addGameObject(torus, new Vector3f(x * spacing, 0, z * spacing - 10),
        // shader);
        // scene.addGameObject(cube, new Vector3f(x * spacing * 3, 0, z * spacing - 10 *
        // 3), shader);
        // }
        // }

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