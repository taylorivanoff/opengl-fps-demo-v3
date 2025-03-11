package com.example.core;

import java.io.IOException;

import com.example.state.GameStateManager;
import com.example.state.PlayState;

public class GameMain {
    public static void main(String[] args) throws IOException {
        WindowManager windowManager = new WindowManager();
        windowManager.init();

        GameStateManager gameStateManager = new GameStateManager();
        gameStateManager.pushState(new PlayState(windowManager));

        while (!windowManager.shouldClose()) {
            float deltaTime = windowManager.calculateDeltaTime();
            gameStateManager.update(deltaTime);
            windowManager.update();
        }

        gameStateManager.popState();
        windowManager.cleanup();
    }
}