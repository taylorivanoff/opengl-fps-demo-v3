package com.example.ui;

public class HUD {
    private int fps;
    private long lastTime;
    private int frameCount;

    public HUD() {
        lastTime = System.currentTimeMillis();
    }

    public void update() {
        frameCount++;
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTime >= 1000) {
            fps = frameCount;
            frameCount = 0;
            lastTime = currentTime;
            System.out.println("Current FPS: " + fps);
        }
    }

    public void render() {
        // Render FPS on the screen.
        // Here you might use a text rendering library.
        // GL11.glColor3f(1.0f, 1.0f, 1.0f); // White color for text.
        // For illustration, the FPS is printed to the console.
    }
}