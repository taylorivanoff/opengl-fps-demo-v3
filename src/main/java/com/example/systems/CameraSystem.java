package com.example.systems;

import org.joml.Vector2f;

import com.example.ecs.Entity;
import com.example.ecs.components.CameraComponent;
import com.example.ecs.components.TransformComponent;
import com.example.input.InputManager;
import com.example.utils.Logger;
import com.example.utils.Logger.Level;

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
        CameraComponent camera = cameraEntity.getComponent(CameraComponent.class);
        TransformComponent playerTransform = playerEntity.getComponent(TransformComponent.class);

        if (camera == null || playerTransform == null)
            return;

        // 🔹 Mouse Look Rotation
        Vector2f mouseDelta = inputManager.getMouseDelta();
        camera.rotation.x -= mouseDelta.y * sensitivity; // Pitch (Up/Down)
        camera.rotation.y += mouseDelta.x * sensitivity; // Yaw (Left/Right)

        // 🔹 Clamp Pitch (Prevents flipping over)
        camera.rotation.x = Math.max(-89.0f, Math.min(89.0f, camera.rotation.x));

        // 🔹 Follow Player (Attach Camera to Player's Position)
        camera.position.set(playerTransform.position);
        Logger.log(Level.DEBUG, "cameraPos: " + camera.position);
        Logger.log(Level.DEBUG, "cameraRot: " + camera.rotation);
    }

}