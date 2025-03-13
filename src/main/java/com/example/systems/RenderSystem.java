package com.example.systems;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

import com.example.ecs.Entity;
import com.example.ecs.EntityManager;
import com.example.ecs.components.*;

public class RenderSystem extends GameSystem {
    private Entity cameraEntity;
    private EntityManager entityManager;

    private Matrix4f viewMatrix = new Matrix4f();

    public RenderSystem(Entity cameraEntity, EntityManager entityManager) {
        this.cameraEntity = cameraEntity;
        this.entityManager = entityManager;
    }

    @Override
    public void update(float deltaTime) {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer matrixBuffer = stack.mallocFloat(16);

            CameraComponent camera = cameraEntity.getComponent(CameraComponent.class);
            Matrix4f projectionMatrix = camera.getProjectionMatrix();
            Matrix4f viewMatrix = getViewMatrix(camera);

            for (Entity entity : entityManager.getEntitiesWith(RenderableComponent.class)) {
                TransformComponent transform = entity.getComponent(TransformComponent.class);
                RenderableComponent model = entity.getComponent(RenderableComponent.class);

                if (transform != null && model != null) {
                    model.shader.bind();

                    Matrix4f modelMatrix = new Matrix4f().translate(transform.position);
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

    public Matrix4f getViewMatrix(CameraComponent camera) {
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