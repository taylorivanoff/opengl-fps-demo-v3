package com.example.systems;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

import com.example.core.WindowManager;
import com.example.ecs.GameSystem;

public class InputSystem extends GameSystem {
    private long window;

    // Pass in the WindowManager so we can get the window handle
    public InputSystem(WindowManager windowManager) {
        this.window = windowManager.getWindowHandle();
    }

    @Override
    public void update(float deltaTime) {
        // Example: Close the window if the ESC key is pressed.
        if (glfwGetKey(window, GLFW_KEY_ESCAPE) == GLFW_PRESS) {
            glfwSetWindowShouldClose(window, true);
        }

        // Additional input handling (mouse, keyboard, etc.) can be added here.
    }
}
