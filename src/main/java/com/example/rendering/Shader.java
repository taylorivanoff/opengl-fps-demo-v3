package com.example.rendering;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.lwjgl.opengl.GL20;

import com.example.utils.Logger;
import com.example.utils.Logger.Level;

public class Shader {
    private final int programId;

    public Shader(String vertexShaderPath, String fragmentShaderPath) {
        int vertexShader = compileShader(vertexShaderPath, GL20.GL_VERTEX_SHADER);
        int fragmentShader = compileShader(fragmentShaderPath, GL20.GL_FRAGMENT_SHADER);

        programId = GL20.glCreateProgram();
        GL20.glAttachShader(programId, vertexShader);
        GL20.glAttachShader(programId, fragmentShader);
        GL20.glLinkProgram(programId);

        if (GL20.glGetProgrami(programId, GL20.GL_LINK_STATUS) == GL20.GL_FALSE) {
            throw new RuntimeException("Shader program linking failed: " + GL20.glGetProgramInfoLog(programId));
        }

        GL20.glDeleteShader(vertexShader);
        GL20.glDeleteShader(fragmentShader);
    }

    private int compileShader(String filePath, int shaderType) {
        String shaderSource = null;
        try {
            shaderSource = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        int shaderId = GL20.glCreateShader(shaderType);

        GL20.glShaderSource(shaderId, shaderSource);
        GL20.glCompileShader(shaderId);

        if (GL20.glGetShaderi(shaderId, GL20.GL_COMPILE_STATUS) == GL20.GL_FALSE) {
            throw new RuntimeException("Shader compilation failed: " + GL20.glGetShaderInfoLog(shaderId));
        }

        Logger.log(Level.INFO, "Loaded shader: " + filePath);

        return shaderId;
    }

    public void bind() {
        GL20.glUseProgram(programId);
    }

    public void unbind() {
        GL20.glUseProgram(0);
    }

    public void setUniform(String name, FloatBuffer matrixBuffer) {
        int location = GL20.glGetUniformLocation(programId, name);
        if (location != -1) {
            GL20.glUniformMatrix4fv(location, false, matrixBuffer);
        }
    }

    public void setUniform(String name, float x, float y, float z) {
        int location = GL20.glGetUniformLocation(programId, name);
        if (location != -1) {
            GL20.glUniform3f(location, x, y, z);
        }
    }

    public void cleanup() {
        GL20.glDeleteProgram(programId);
    }
}