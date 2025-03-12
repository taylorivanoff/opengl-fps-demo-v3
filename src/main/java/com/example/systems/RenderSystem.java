package com.example.systems;

import java.nio.FloatBuffer;
import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

import com.example.ecs.Entity;
import com.example.ecs.EntityManager;
import com.example.ecs.components.Camera;
import com.example.ecs.components.Player;
import com.example.ecs.components.Renderable;
import com.example.ecs.components.Transform;
import com.example.utils.Logger;
import com.example.utils.Logger.Level;

public class RenderSystem extends GameSystem {
    private EntityManager entityManager;
    private boolean debugMode = true;

    public RenderSystem(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void update(float deltaTime) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer matrixBuffer = stack.mallocFloat(16);

            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            Entity playerEntity = getPlayerEntity();
            if (playerEntity == null) {
                return;
            }

            Camera camera = playerEntity.getComponent(Camera.class);
            if (camera == null) {
                return;
            }

            Matrix4f viewMatrix = camera.getViewMatrix();
            Matrix4f projectionMatrix = camera.getProjectionMatrix();

            List<Entity> entities = entityManager.getEntitiesWith(Renderable.class);

            if (debugMode) {
                Logger.log(Level.DEBUG, "Rendered Entities: " + entities.size());
            }

            for (Entity entity : entities) {
                Transform transform = entity.getComponent(Transform.class);
                Renderable renderable = entity.getComponent(Renderable.class);

                if (transform != null && renderable != null) {
                    renderable.shader.bind();

                    Matrix4f modelMatrix = new Matrix4f()
                            .translate(new Vector3f(transform.position.x, transform.position.y, transform.position.z));
                    modelMatrix.get(matrixBuffer);

                    renderable.shader.setUniform("uModel", matrixBuffer);
                    viewMatrix.get(matrixBuffer);
                    renderable.shader.setUniform("uView", matrixBuffer);
                    projectionMatrix.get(matrixBuffer);
                    renderable.shader.setUniform("uProjection", matrixBuffer);

                    renderable.mesh.render();

                    renderable.shader.unbind();
                }
            }
        }
    }

    private Entity getPlayerEntity() {
        List<Entity> entities = entityManager.getEntitiesWith(Player.class);

        for (Entity entity : entities) {
            if (entity.getComponent(Player.class) != null) {
                return entity;
            }
        }

        return null;
    }
}
