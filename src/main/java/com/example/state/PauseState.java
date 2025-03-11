package com.example.state;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_P;

import com.example.input.InputManager;

public class PauseState extends GameState {
    private InputManager inputManager;

    public PauseState(InputManager inputManager) {
        this.inputManager = inputManager;
    }

    @Override
    public void init() {
        System.out.println("Pause state initialized.");
    }

    @Override
    public void update(float deltaTime) {
        // Wait for the player to press 'P' again to unpause.
        if (inputManager.isKeyDown(GLFW_KEY_P)) {
            System.out.println("Resuming game from pause state...");
            // Signal a state change back to gameplay.
        }
    }

    @Override
    public void cleanup() {
        System.out.println("Cleaning up pause state resources...");
    }
}