package com.example.state;

import java.util.Stack;

public class GameStateManager {
    private Stack<GameState> stateStack;

    public GameStateManager() {
        stateStack = new Stack<>();
    }

    public void pushState(GameState newState) {
        if (currentState() != null) {
            currentState().cleanup();
        }
        stateStack.push(newState);
        newState.init();
    }

    public void popState() {
        if (stateStack.isEmpty())
            return; 

        currentState().cleanup(); 
        stateStack.pop(); 
        if (!stateStack.isEmpty()) {
            currentState().init(); 
        }
    }

    public void changeState(GameState newState) {
        if (!stateStack.isEmpty()) {
            currentState().cleanup(); 
        }
        stateStack.clear(); 
        stateStack.push(newState);
        newState.init();
    }

    public void update(float deltaTime) {
        if (!stateStack.isEmpty()) {
            currentState().update(deltaTime);
        }
    }

    public GameState currentState() {
        return stateStack.isEmpty() ? null : stateStack.peek();
    }
}