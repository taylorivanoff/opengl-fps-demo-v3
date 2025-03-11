package com.example.physics;

import com.example.ecs.Entity;
import com.example.ecs.components.BoundingBoxComponent;
import com.example.ecs.components.TransformComponent;

public class CollisionHandler {

    public boolean checkCollision(Entity e1, Entity e2) {
        TransformComponent t1 = e1.getComponent(TransformComponent.class);
        BoundingBoxComponent b1 = e1.getComponent(BoundingBoxComponent.class);
        TransformComponent t2 = e2.getComponent(TransformComponent.class);
        BoundingBoxComponent b2 = e2.getComponent(BoundingBoxComponent.class);

        if (t1 == null || b1 == null || t2 == null || b2 == null) {
            return false;
        }

        // Calculate min/max coordinates for e1.
        float t1MinX = t1.x - b1.width / 2;
        float t1MaxX = t1.x + b1.width / 2;
        float t1MinY = t1.y - b1.height / 2;
        float t1MaxY = t1.y + b1.height / 2;
        float t1MinZ = t1.z - b1.depth / 2;
        float t1MaxZ = t1.z + b1.depth / 2;

        // Calculate min/max coordinates for e2.
        float t2MinX = t2.x - b2.width / 2;
        float t2MaxX = t2.x + b2.width / 2;
        float t2MinY = t2.y - b2.height / 2;
        float t2MaxY = t2.y + b2.height / 2;
        float t2MinZ = t2.z - b2.depth / 2;
        float t2MaxZ = t2.z + b2.depth / 2;

        // Check for overlap along each axis.
        return (t1MinX <= t2MaxX && t1MaxX >= t2MinX) &&
                (t1MinY <= t2MaxY && t1MaxY >= t2MinY) &&
                (t1MinZ <= t2MaxZ && t1MaxZ >= t2MinZ);
    }

    public void handleCollisions(Iterable<Entity> entities) {
        // Naive O(n^2) collision detection.
        for (Entity e1 : entities) {
            for (Entity e2 : entities) {
                if (e1.getId() >= e2.getId())
                    continue; // Avoid duplicate checks.
                if (checkCollision(e1, e2)) {
                    System.out.println("Collision detected between entities " + e1.getId() + " and " + e2.getId());
                    // Add collision resolution logic here.
                }
            }
        }
    }
}