package com.example.systems;

import org.lwjgl.opengl.GL11;

import com.example.ecs.GameSystem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class Particle {
    float x, y, z;
    float vx, vy, vz;
    float lifetime;

    public Particle(float x, float y, float z, float vx, float vy, float vz, float lifetime) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.vx = vx;
        this.vy = vy;
        this.vz = vz;
        this.lifetime = lifetime;
    }

    public void update(float deltaTime) {
        x += vx * deltaTime;
        y += vy * deltaTime;
        z += vz * deltaTime;
        lifetime -= deltaTime;
    }
}

public class ParticleSystem extends GameSystem {
    private List<Particle> particles = new ArrayList<>();

    public void spawnParticle(float x, float y, float z) {
        // Create a particle with random velocity and a fixed lifetime.
        float vx = (float) (Math.random() - 0.5f) * 2;
        float vy = (float) (Math.random() - 0.5f) * 2;
        float vz = (float) (Math.random() - 0.5f) * 2;
        particles.add(new Particle(x, y, z, vx, vy, vz, 2.0f));
    }

    @Override
    public void update(float deltaTime) {
        Iterator<Particle> iter = particles.iterator();
        while (iter.hasNext()) {
            Particle p = iter.next();
            p.update(deltaTime);
            if (p.lifetime <= 0) {
                iter.remove();
            }
        }
    }

    public void render() {
        // Render particles as points.
        GL11.glPointSize(5);
        GL11.glBegin(GL11.GL_POINTS);
        for (Particle p : particles) {
            GL11.glVertex3f(p.x, p.y, p.z);
        }
        GL11.glEnd();
    }
}