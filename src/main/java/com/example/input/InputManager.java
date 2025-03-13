package com.example.input;

import static org.lwjgl.glfw.GLFW.*;

import java.util.HashMap;
import java.util.Map;

import org.joml.Vector2f;
import org.lwjgl.glfw.*;

import com.example.input.InputBindingManager.Action;

public class InputManager {
    private long window;
    private Map<Integer, Boolean> keyStates = new HashMap<>();
    private Map<Integer, Boolean> mouseButtonStates = new HashMap<>();
    private Vector2f mousePosition = new Vector2f();
    private Vector2f lastMousePosition = new Vector2f();
    private Vector2f mouseDelta = new Vector2f();
    private boolean firstMouseMove = true;
    private InputBindingManager bindingManager;

    public InputManager(long window, InputBindingManager bindingManager) {
        this.window = window;
        this.bindingManager = bindingManager;

        GLFW.glfwSetKeyCallback(window, new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                keyStates.put(key, action != GLFW_RELEASE);
            }
        });

        GLFW.glfwSetCursorPosCallback(window, new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                if (firstMouseMove) {
                    lastMousePosition.set((float) xpos, (float) ypos);
                    firstMouseMove = false;
                }

                mousePosition.set((float) xpos, (float) ypos);
                mouseDelta.set(mousePosition).sub(lastMousePosition);
                lastMousePosition.set(mousePosition);
            }
        });

        GLFW.glfwSetMouseButtonCallback(window, new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                mouseButtonStates.put(button, action != GLFW_RELEASE);
            }
        });
    }

    public boolean isKeyPressed(int key) {
        return keyStates.getOrDefault(key, false);
    }

    public boolean isMouseButtonPressed(int button) {
        return mouseButtonStates.getOrDefault(button, false);
    }

    public Vector2f getMouseDelta() {
        Vector2f delta = new Vector2f(mouseDelta);
        mouseDelta.set(0, 0);
        return delta;
    }

    public boolean isActionPressed(Action action) {
        int key = bindingManager.getKeyForAction(action);
        if (key == -1)
            return false;
        return isKeyPressed(key) || isMouseButtonPressed(key);
    }

    public void disableCursor() {
        GLFW.glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
    }

    public void enableCursor() {
        GLFW.glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
    }
}