package com.example.input;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;

public class InputBindingManager {
    private Map<String, Integer> actionKeyMap = new HashMap<>();

    public InputBindingManager() {
        actionKeyMap.put("MOVE_LEFT", GLFW_KEY_A);
        actionKeyMap.put("MOVE_RIGHT", GLFW_KEY_D);
        actionKeyMap.put("MOVE_FORWARD", GLFW_KEY_W);
        actionKeyMap.put("MOVE_BACKWARD", GLFW_KEY_S);
        actionKeyMap.put("JUMP", GLFW_KEY_SPACE);
        actionKeyMap.put("FIRE", GLFW_MOUSE_BUTTON_LEFT);
    }

    public int getKeyForAction(String action) {
        return actionKeyMap.getOrDefault(action, -1);
    }

    public void setKeyForAction(String action, int key) {
        actionKeyMap.put(action, key);
    }
}