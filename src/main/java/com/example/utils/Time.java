package com.example.utils;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class Time {
    private static double lastTime = 0;
    private static double deltaTime = 0;

    public static void update() {
        double currentTime = glfwGetTime(); // Convert to seconds
        deltaTime = currentTime - lastTime;
        lastTime = currentTime;
    }

    public static float getDeltaTime() {
        return (float) deltaTime;
    }
}