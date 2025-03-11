package com.example.systems;

import com.example.ecs.Entity;
import com.example.ecs.EntityManager;
import com.example.ecs.GameSystem;
import com.example.ecs.components.BoundingBoxComponent;
import com.example.ecs.components.TransformComponent;

public class DebugOverlaySystem extends GameSystem {
    private EntityManager entityManager;

    public DebugOverlaySystem(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void update(float deltaTime) {
        // No update logic; rendering debug information only.
    }

    public void render() {
        // Draw red bounding boxes for entities that have both Transform and
        // BoundingBox.
        for (Entity entity : entityManager.getAllEntities()) {
            TransformComponent t = entity.getComponent(TransformComponent.class);
            BoundingBoxComponent b = entity.getComponent(BoundingBoxComponent.class);
            if (t != null && b != null) {
                drawBoundingBox(t, b);
            }
        }
    }

    private void drawBoundingBox(TransformComponent t, BoundingBoxComponent b) {
        float halfWidth = b.width / 2;
        float halfDepth = b.depth / 2;
        float x = t.x;
        float z = t.z;
        // GL11.glBegin(GL11.GL_LINE_LOOP);
        // GL11.glVertex3f(x - halfWidth, t.y, z - halfDepth);
        // GL11.glVertex3f(x + halfWidth, t.y, z - halfDepth);
        // GL11.glVertex3f(x + halfWidth, t.y, z + halfDepth);
        // GL11.glVertex3f(x - halfWidth, t.y, z + halfDepth);
        // GL11.glEnd();
    }
}