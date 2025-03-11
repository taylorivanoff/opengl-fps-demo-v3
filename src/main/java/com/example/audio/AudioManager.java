package com.example.audio;

import java.nio.IntBuffer;

import org.lwjgl.openal.AL;
import static org.lwjgl.openal.AL10.*;
import org.lwjgl.openal.ALC;
import static org.lwjgl.openal.ALC10.*;
import org.lwjgl.openal.ALCCapabilities;
import static org.lwjgl.system.MemoryUtil.NULL;

public class AudioManager {
    private long device;
    private long context;

    public AudioManager() {
        device = alcOpenDevice((String) null);
        if (device == NULL) {
            throw new IllegalStateException("Failed to open the default OpenAL device.");
        }
        ALCCapabilities deviceCaps = ALC.createCapabilities(device);
        context = alcCreateContext(device, (IntBuffer) null);
        alcMakeContextCurrent(context);
        AL.createCapabilities(deviceCaps);
        System.out.println("AudioManager initialized.");
    }

    public int loadSound(String filePath) {
        int buffer = alGenBuffers();
        System.out.println("Loading sound from: " + filePath);
        return buffer;
    }

    public void playSound(int buffer) {
        int source = alGenSources();
        alSourcei(source, AL_BUFFER, buffer);
        alSourcePlay(source);
        System.out.println("Playing sound from buffer: " + buffer);
    }

    public void cleanup() {
        alcDestroyContext(context);
        alcCloseDevice(device);
        System.out.println("AudioManager cleaned up.");
    }
}