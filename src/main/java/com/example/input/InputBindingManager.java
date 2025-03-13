package com.example.input;

import static org.lwjgl.glfw.GLFW.*;

import java.util.HashMap;
import java.util.Map;

public class InputBindingManager {
    public enum Action {
        MOVE_LEFT, MOVE_RIGHT, MOVE_FORWARD, MOVE_BACKWARD,
        JUMP, CROUCH, SPRINT,
        FIRE,
    }

    private final Map<Action, Integer> actionKeyMap = new HashMap<>();

    public InputBindingManager() {
        actionKeyMap.put(Action.MOVE_LEFT, GLFW_KEY_A);
        actionKeyMap.put(Action.MOVE_RIGHT, GLFW_KEY_D);
        actionKeyMap.put(Action.MOVE_FORWARD, GLFW_KEY_W);
        actionKeyMap.put(Action.MOVE_BACKWARD, GLFW_KEY_S);

        actionKeyMap.put(Action.JUMP, GLFW_KEY_SPACE);
        actionKeyMap.put(Action.CROUCH, GLFW_KEY_LEFT_CONTROL);
        actionKeyMap.put(Action.SPRINT, GLFW_KEY_LEFT_SHIFT);

        actionKeyMap.put(Action.FIRE, GLFW_MOUSE_BUTTON_LEFT);
    }

    public int getKeyForAction(Action action) {
        return actionKeyMap.getOrDefault(action, -1);
    }

    public void setKeyForAction(Action action, int key) {
        actionKeyMap.put(action, key);
    }
}