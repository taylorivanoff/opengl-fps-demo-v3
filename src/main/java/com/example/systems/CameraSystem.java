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
        CameraComponent cameraComponent = cameraEntity.getComponent(CameraComponent.class);
        TransformComponent playerTransform = playerEntity.getComponent(TransformComponent.class);

        if (cameraComponent == null || playerTransform == null)
            return;

        Vector2f mouseDelta = inputManager.getMouseDelta();

        cameraComponent.rotation.x = Math.max(-89.0f, Math.min(89.0f, cameraComponent.rotation.x));
        cameraComponent.rotation.x -= mouseDelta.y * sensitivity;

        cameraComponent.rotation.y += mouseDelta.x * sensitivity;

        cameraComponent.position.set(playerTransform.position);
        playerTransform.rotation = cameraComponent.rotation;
        Logger.log(Level.DEBUG, "cameraPos: " + cameraComponent.position);
        Logger.log(Level.DEBUG, "cameraRot: " + cameraComponent.rotation);
    }

}