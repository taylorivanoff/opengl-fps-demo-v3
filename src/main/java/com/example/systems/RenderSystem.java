package com.example.systems;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

import com.example.core.GameConstants;
import com.example.ecs.Entity;
import com.example.ecs.EntityManager;
import com.example.ecs.components.Renderable;
import com.example.ecs.components.Transform;

public class RenderSystem extends GameSystem {
    private Entity camera;
    private EntityManager entityManager;

    private float fov = 65.0f;
    private float aspectRatio = GameConstants.WINDOW_WIDTH / GameConstants.WINDOW_HEIGHT;
    private float nearPlane = 0.1f;
    private float farPlane = 1000f;

    private Matrix4f viewMatrix = new Matrix4f();

    public RenderSystem(Entity cameraEntity, EntityManager entityManager) {
        this.camera = cameraEntity;
        this.entityManager = entityManager;
    }

    @Override
    public void update(float deltaTime) {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer matrixBuffer = stack.mallocFloat(16);

            Transform cameraTransform = camera.getComponent(Transform.class);
            Matrix4f projectionMatrix = getProjectionMatrix();
            Matrix4f viewMatrix = getViewMatrix(cameraTransform);

            for (Entity entity : entityManager.getEntitiesWith(Renderable.class)) {
                Transform transform = entity.getComponent(Transform.class);
                Renderable model = entity.getComponent(Renderable.class);

                if (transform != null && model != null) {
                    model.shader.bind();

                    Matrix4f modelMatrix = new Matrix4f()
                            .scale(transform.scale)
                            .rotate(transform.rotation)
                            .translate(transform.position);

                    modelMatrix.get(matrixBuffer);
                    model.shader.setUniform("uModel", matrixBuffer);

                    viewMatrix.get(matrixBuffer);
                    model.shader.setUniform("uView", matrixBuffer);

                    projectionMatrix.get(matrixBuffer);
                    model.shader.setUniform("uProjection", matrixBuffer);

                    model.mesh.render();

                    model.shader.unbind();
                }
            }
        }
    }

    public Matrix4f getProjectionMatrix() {
        return new Matrix4f().perspective((float) Math.toRadians(fov), (float) aspectRatio, nearPlane, farPlane);
    }

    public Matrix4f getViewMatrix(Transform camera) {
        Vector3f forward = new Vector3f();

        float yaw = (float) Math.toRadians(camera.rotation.y);
        float pitch = (float) Math.toRadians(camera.rotation.x);

        forward.x = (float) (Math.cos(yaw) * Math.cos(pitch));
        forward.y = (float) Math.sin(pitch);
        forward.z = (float) (Math.sin(yaw) * Math.cos(pitch));

        forward.normalize();

        Vector3f viewPosition = new Vector3f(camera.position);
        Vector3f target = new Vector3f(viewPosition).add(forward);
        Vector3f up = new Vector3f(0, 1, 0);

        return viewMatrix.identity().lookAt(viewPosition, target, up);
    }
}