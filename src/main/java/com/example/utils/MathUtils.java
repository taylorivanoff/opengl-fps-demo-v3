package com.example.utils;

public class MathUtils {
    public static float lerp(float a, float b, float t) {
        return a + t * (b - a);
    }
}
