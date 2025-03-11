package com.example.input;

import java.util.HashSet;
import java.util.Set;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import org.lwjgl.glfw.GLFWKeyCallback;

import com.example.core.WindowManager;

public class InputManager {
    private long window;
    private Set<Integer> pressedKeys = new HashSet<>();
    private GLFWKeyCallback keyCallback;

    public InputManager(WindowManager windowManager) {
        this.window = windowManager.getWindowHandle();
        init();
    }

    private void init() {
        glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (action == GLFW_PRESS) {
                    pressedKeys.add(key);
                } else if (action == GLFW_RELEASE) {
                    pressedKeys.remove(key);
                }
            }
        });
    }

    public boolean isKeyDown(int key) {
        return pressedKeys.contains(key);
    }

    public void cleanup() {
        if (keyCallback != null) {
            keyCallback.free();
        }
    }
}