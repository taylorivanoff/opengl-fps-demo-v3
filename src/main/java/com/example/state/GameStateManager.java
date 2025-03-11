package com.example.state;

import java.util.Stack;

public class GameStateManager {
    private Stack<GameState> stateStack;

    public GameStateManager() {
        stateStack = new Stack<>();
    }

    // Push a new state onto the stack and initialize it
    public void pushState(GameState newState) {
        if (currentState() != null) {
            currentState().cleanup(); // Optionally cleanup the current state before pushing a new one
        }
        stateStack.push(newState);
        newState.init();
    }

    // Pop the current state from the stack and switch to the previous one
    public void popState() {
        if (stateStack.isEmpty())
            return; // Can't pop if there's no state

        currentState().cleanup(); // Cleanup the current state before switching
        stateStack.pop(); // Remove the current state
        if (!stateStack.isEmpty()) {
            currentState().init(); // Re-initialize the previous state
        }
    }

    // Replace the current state with a new one, without adding to the stack
    public void changeState(GameState newState) {
        if (!stateStack.isEmpty()) {
            currentState().cleanup(); // Cleanup the current state before replacing
        }
        stateStack.clear(); // Clear the stack before adding the new state
        stateStack.push(newState);
        newState.init();
    }

    // Update the current active state (calls the state's update method)
    public void update(float deltaTime) {
        if (!stateStack.isEmpty()) {
            currentState().update(deltaTime);
        }
    }

    // Get the current active state
    public GameState currentState() {
        return stateStack.isEmpty() ? null : stateStack.peek();
    }
}