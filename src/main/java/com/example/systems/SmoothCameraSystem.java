package com.example.systems;

import com.example.ecs.GameSystem;
import com.example.ecs.components.TransformComponent;

public class SmoothCameraSystem extends GameSystem {
    private TransformComponent cameraTransform;
    private TransformComponent targetTransform;
    private float damping; // e.g., 0.1f for smooth following

    public SmoothCameraSystem(TransformComponent cameraTransform, TransformComponent targetTransform, float damping) {
        this.cameraTransform = cameraTransform;
        this.targetTransform = targetTransform;
        this.damping = damping;
    }

    @Override
    public void update(float deltaTime) {
        // Interpolate the camera position toward the target's position.
        cameraTransform.x += (targetTransform.x - cameraTransform.x) * damping * deltaTime;
        cameraTransform.y += (targetTransform.y - cameraTransform.y) * damping * deltaTime;
        cameraTransform.z += (targetTransform.z - cameraTransform.z) * damping * deltaTime;
    }
}
