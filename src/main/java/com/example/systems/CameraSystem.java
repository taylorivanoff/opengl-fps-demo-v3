package com.example.systems;

import org.joml.Vector2f;

import com.example.ecs.Entity;
import com.example.ecs.components.Transform;
import com.example.input.InputManager;

public class CameraSystem extends GameSystem {
    private Entity cameraEntity;
    private Entity playerEntity;
    private InputManager inputManager;

    private float sensitivity = 0.1f;

    public CameraSystem(Entity cameraEntity, Entity playerEntity, InputManager inputManager) {
        this.cameraEntity = cameraEntity;
        this.playerEntity = playerEntity;
        this.inputManager = inputManager;
    }

    @Override
    public void update(float deltaTime) {
        Transform camera = cameraEntity.getComponent(Transform.class);
        Transform player = playerEntity.getComponent(Transform.class);

        if (camera == null || player == null)
            return;

        Vector2f mouseDelta = inputManager.getMouseDelta();

        camera.rotation.x -= mouseDelta.y * sensitivity;
        camera.rotation.y += mouseDelta.x * sensitivity;

        camera.rotation.x = Math.max(-89.0f, Math.min(89.0f, camera.rotation.x));

        camera.position.set(player.position);
        player.rotation = camera.rotation;
    }

}