package com.example.systems;

import org.joml.Vector3f;
import static org.lwjgl.glfw.GLFW.*;

import com.example.core.WindowManager;
import com.example.ecs.Entity;
import com.example.ecs.EntityManager;
import com.example.ecs.GameSystem;
import com.example.ecs.components.CameraComponent;
import com.example.ecs.components.TransformComponent;

public class CameraControlSystem extends GameSystem {
    private WindowManager windowManager;
    private EntityManager entityManager;
    private float moveSpeed = 5.0f;

    public CameraControlSystem(WindowManager windowManager, EntityManager entityManager) {
        this.windowManager = windowManager;
        this.entityManager = entityManager;
    }

    @Override
    public void update(float deltaTime) {
        for (Entity entity : entityManager.getAllEntities()) {
            CameraComponent camera = entity.getComponent(CameraComponent.class);
            TransformComponent transform = entity.getComponent(TransformComponent.class);

            if (camera != null && transform != null) {
                // Handle mouse movement
                double[] mouseDeltas = windowManager.getMouseDeltas();
                camera.processMouseMovement((float) mouseDeltas[0], (float) mouseDeltas[1], true);

                // Get the camera's orientation vectors
                Vector3f front = camera.getFront();
                Vector3f right = new Vector3f(front).cross(new Vector3f(0, 1, 0)).normalize();

                // Handle keyboard input for movement
                if (glfwGetKey(windowManager.getWindowHandle(), GLFW_KEY_W) == GLFW_PRESS) {
                    transform.x += front.x * moveSpeed * deltaTime;
                    transform.z += front.z * moveSpeed * deltaTime;
                }
                if (glfwGetKey(windowManager.getWindowHandle(), GLFW_KEY_S) == GLFW_PRESS) {
                    transform.x -= front.x * moveSpeed * deltaTime;
                    transform.z -= front.z * moveSpeed * deltaTime;
                }
                if (glfwGetKey(windowManager.getWindowHandle(), GLFW_KEY_A) == GLFW_PRESS) {
                    transform.x -= right.x * moveSpeed * deltaTime;
                    transform.z -= right.z * moveSpeed * deltaTime;
                }
                if (glfwGetKey(windowManager.getWindowHandle(), GLFW_KEY_D) == GLFW_PRESS) {
                    transform.x += right.x * moveSpeed * deltaTime;
                    transform.z += right.z * moveSpeed * deltaTime;
                }

                // Update camera position
                camera.setPosition(new Vector3f(transform.x, transform.y, transform.z));
            }
        }
    }
}
