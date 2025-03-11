package com.example.systems;

public class ParticleEmitter {
    public float x, y, z;
    public float emissionRate; // Particles per second
    private float emissionAccumulator;

    public ParticleEmitter(float x, float y, float z, float emissionRate) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.emissionRate = emissionRate;
        this.emissionAccumulator = 0f;
    }

    // Call this in the game loop to spawn particles at the desired rate.
    public void update(float deltaTime, ParticleSystem particleSystem) {
        emissionAccumulator += deltaTime * emissionRate;
        while (emissionAccumulator >= 1f) {
            particleSystem.spawnParticle(x, y, z);
            emissionAccumulator -= 1f;
        }
    }
}