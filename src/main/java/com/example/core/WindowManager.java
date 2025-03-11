package com.example.core;

import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.GL_RENDERER;
import static org.lwjgl.opengl.GL11.GL_VENDOR;
import static org.lwjgl.opengl.GL11.GL_VERSION;
import static org.lwjgl.opengl.GL11.glGetString;
import static org.lwjgl.system.MemoryUtil.NULL;

public class WindowManager {
    private long window;
    private double lastTime;
    private double lastMouseX = 400;
    private double lastMouseY = 300;
    private boolean firstMouse = true;

    public void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfwDefaultWindowHints();

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
        glfwShowWindow(window);
        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);

        GL.createCapabilities();

        System.out.println("OpenGL Version: " + glGetString(GL_VERSION));
        System.out.println("OpenGL Vendor: " + glGetString(GL_VENDOR));
        System.out.println("OpenGL Renderer: " + glGetString(GL_RENDERER));

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        // GL11.glEnable(GL11.GL_CULL_FACE);
        // GL11.glDepthFunc(GL11.GL_LESS);
        // GL11.glCullFace(GL11.GL_BACK);

        GL11.glClearColor(0.1f, 0.1f, 0.2f, 1.0f);

        lastTime = glfwGetTime();
    }

    public double[] getMouseDeltas() {
        double[] pos = new double[2];
        double[] xpos = new double[1];
        double[] ypos = new double[1];
        glfwGetCursorPos(window, xpos, ypos);
        pos[0] = xpos[0];
        pos[1] = ypos[0];

        if (firstMouse) {
            lastMouseX = pos[0];
            lastMouseY = pos[1];
            firstMouse = false;
            return new double[] { 0, 0 };
        }

        double xoffset = pos[0] - lastMouseX;
        double yoffset = lastMouseY - pos[1];

        lastMouseX = pos[0];
        lastMouseY = pos[1];

        return new double[] { xoffset, yoffset };
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
