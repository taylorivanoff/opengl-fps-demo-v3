package com.example.utils;

import java.awt.Color;

public class ColorUtils {
    public static Color blend(Color c1, Color c2, float t) {
        int red = (int) (c1.getRed() + t * (c2.getRed() - c1.getRed()));
        int green = (int) (c1.getGreen() + t * (c2.getGreen() - c1.getGreen()));
        int blue = (int) (c1.getBlue() + t * (c2.getBlue() - c1.getBlue()));
        return new Color(red, green, blue);
    }
}
