package com.example.systems;

import org.joml.Vector3f;
import static org.lwjgl.glfw.GLFW.*;

import com.example.core.WindowManager;
import com.example.ecs.Entity;
import com.example.ecs.EntityManager;
import com.example.ecs.components.Camera;
import com.example.ecs.components.Transform;

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
            Camera camera = entity.getComponent(Camera.class);
            Transform transform = entity.getComponent(Transform.class);

            if (camera != null && transform != null) {
                double[] mouseDeltas = windowManager.getMouseDeltas();
                camera.processMouseMovement((float) mouseDeltas[0], (float) mouseDeltas[1], true);

                Vector3f front = camera.getFront();
                Vector3f right = new Vector3f(front).cross(new Vector3f(0, 1, 0)).normalize();

                if (glfwGetKey(windowManager.getWindowHandle(), GLFW_KEY_W) == GLFW_PRESS) {
                    transform.position.x += front.x * moveSpeed * deltaTime;
                    transform.position.z += front.z * moveSpeed * deltaTime;
                }
                if (glfwGetKey(windowManager.getWindowHandle(), GLFW_KEY_S) == GLFW_PRESS) {
                    transform.position.x -= front.x * moveSpeed * deltaTime;
                    transform.position.z -= front.z * moveSpeed * deltaTime;
                }
                if (glfwGetKey(windowManager.getWindowHandle(), GLFW_KEY_A) == GLFW_PRESS) {
                    transform.position.x -= right.x * moveSpeed * deltaTime;
                    transform.position.z -= right.z * moveSpeed * deltaTime;
                }
                if (glfwGetKey(windowManager.getWindowHandle(), GLFW_KEY_D) == GLFW_PRESS) {
                    transform.position.x += right.x * moveSpeed * deltaTime;
                    transform.position.z += right.z * moveSpeed * deltaTime;
                }

                camera.setPosition(new Vector3f(transform.position.x, transform.position.y, transform.position.z));
            }
        }
    }
}
