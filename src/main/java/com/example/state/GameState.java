package com.example.state;

import com.example.ecs.EntityManager;

public abstract class GameState {
    protected EntityManager entityManager;

    public GameState() {
        entityManager = new EntityManager();
    }

    public abstract void init();

    public void update(float deltaTime) {
        entityManager.update(deltaTime);
    }

    public void cleanup() {
        entityManager.clear();
    }
}