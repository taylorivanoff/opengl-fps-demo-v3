package com.example.audio;

public class SoundSource {
    private int bufferId;
    private AudioManager audioManager;

    public SoundSource(AudioManager audioManager, int bufferId) {
        this.audioManager = audioManager;
        this.bufferId = bufferId;
    }

    public void play() {
        audioManager.playSound(bufferId);
    }
}