package com.example.state;

import com.example.core.WindowManager;
import com.example.ecs.EntityManager;

public abstract class GameState {
    protected WindowManager windowManager;
    protected EntityManager ecs;

    public GameState(WindowManager windowManager) {
        this.ecs = new EntityManager();
        this.windowManager = windowManager;
    }

    public abstract void init();

    public void update(float deltaTime) {
        ecs.updateSystems(deltaTime);
    }

    public void cleanup() {
        ecs.clear();
    }
}