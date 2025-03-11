package com.example.events;

public class PlayerFiredEvent extends Event {
    public int playerId;
    public String weapon;

    public PlayerFiredEvent(int playerId, String weapon) {
        this.playerId = playerId;
        this.weapon = weapon;
    }
}