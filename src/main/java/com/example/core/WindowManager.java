package com.example.core;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class WindowManager {
    private long window;
    private double lastTime;

    public void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfwDefaultWindowHints();

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);

        window = glfwCreateWindow(GameConstants.WINDOW_WIDTH, GameConstants.WINDOW_HEIGHT, GameConstants.WINDOW_TITLE,
                NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        if (vidmode != null) {
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - GameConstants.WINDOW_WIDTH) / 2,
                    (vidmode.height() - GameConstants.WINDOW_HEIGHT) / 2);
        }

        glfwSetKeyCallback(window, (w, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                glfwSetWindowShouldClose(window, true);
            }
        });

        glfwMakeContextCurrent(window);
        glfwSwapInterval(0);
        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);

        GL.createCapabilities();

        System.out.println("OpenGL Version: " + glGetString(GL_VERSION));
        System.out.println("OpenGL Vendor: " + glGetString(GL_VENDOR));
        System.out.println("OpenGL Renderer: " + glGetString(GL_RENDERER));

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDepthFunc(GL11.GL_LESS);
        GL11.glCullFace(GL11.GL_BACK);

        GL11.glClearColor(0.1f, 0.1f, 0.2f, 1.0f);

        glfwShowWindow(window);

        lastTime = glfwGetTime();
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(window);
    }

    public void update() {
        glfwSwapBuffers(window);
        glfwPollEvents();
    }

    public void cleanup() {
        glfwDestroyWindow(window);
        glfwTerminate();
    }

    public long getWindowHandle() {
        return window;
    }

    public double getTime() {
        return glfwGetTime();
    }

    float calculateDeltaTime() {
        double currentTime = glfwGetTime();
        float deltaTime = (float) (currentTime - lastTime);
        lastTime = currentTime;
        return deltaTime;
    }
}
