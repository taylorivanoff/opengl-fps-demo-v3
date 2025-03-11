package com.example.rendering;

import java.util.ArrayList;
import java.util.List;

public class MeshFactory {
    public static Mesh createCube(float size) {
        float halfSize = size / 2.0f;
        
        // Vertex positions
        List<Float> vertices = new ArrayList<>();
        // Front face
        vertices.add(-halfSize); vertices.add(-halfSize); vertices.add(halfSize);  // Bottom-left
        vertices.add(halfSize);  vertices.add(-halfSize); vertices.add(halfSize);  // Bottom-right
        vertices.add(halfSize);  vertices.add(halfSize);  vertices.add(halfSize);  // Top-right
        vertices.add(-halfSize); vertices.add(halfSize);  vertices.add(halfSize);  // Top-left
        
        // Back face
        vertices.add(-halfSize); vertices.add(-halfSize); vertices.add(-halfSize);
        vertices.add(-halfSize); vertices.add(halfSize);  vertices.add(-halfSize);
        vertices.add(halfSize);  vertices.add(halfSize);  vertices.add(-halfSize);
        vertices.add(halfSize);  vertices.add(-halfSize); vertices.add(-halfSize);

        // Normal vectors
        List<Float> normals = new ArrayList<>();
        // Front face normals
        for (int i = 0; i < 4; i++) {
            normals.add(0.0f); normals.add(0.0f); normals.add(1.0f);
        }
        // Back face normals
        for (int i = 0; i < 4; i++) {
            normals.add(0.0f); normals.add(0.0f); normals.add(-1.0f);
        }

        // Texture coordinates
        List<Float> texCoords = new ArrayList<>();
        // Front face
        texCoords.add(0.0f); texCoords.add(0.0f);
        texCoords.add(1.0f); texCoords.add(0.0f);
        texCoords.add(1.0f); texCoords.add(1.0f);
        texCoords.add(0.0f); texCoords.add(1.0f);
        // Back face
        texCoords.add(0.0f); texCoords.add(0.0f);
        texCoords.add(0.0f); texCoords.add(1.0f);
        texCoords.add(1.0f); texCoords.add(1.0f);
        texCoords.add(1.0f); texCoords.add(0.0f);

        // Indices
        List<Integer> indices = new ArrayList<>();
        // Front face
        indices.add(0); indices.add(1); indices.add(2);
        indices.add(2); indices.add(3); indices.add(0);
        // Back face
        indices.add(4); indices.add(5); indices.add(6);
        indices.add(6); indices.add(7); indices.add(4);
        // Right face
        indices.add(1); indices.add(7); indices.add(6);
        indices.add(6); indices.add(2); indices.add(1);
        // Left face
        indices.add(4); indices.add(0); indices.add(3);
        indices.add(3); indices.add(5); indices.add(4);
        // Top face
        indices.add(3); indices.add(2); indices.add(6);
        indices.add(6); indices.add(5); indices.add(3);
        // Bottom face
        indices.add(4); indices.add(7); indices.add(1);
        indices.add(1); indices.add(0); indices.add(4);

        return new Mesh(vertices, normals, texCoords, indices, null);
    }
} 