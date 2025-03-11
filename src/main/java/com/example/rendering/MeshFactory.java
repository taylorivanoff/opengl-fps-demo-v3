package com.example.rendering;

import java.util.ArrayList;
import java.util.List;

public class MeshFactory {
    public static Mesh createCube(float size) {
        float halfSize = size / 2.0f;
        
        List<Float> vertices = new ArrayList<>();
        vertices.add(-halfSize); vertices.add(-halfSize); vertices.add(halfSize);  
        vertices.add(halfSize);  vertices.add(-halfSize); vertices.add(halfSize);  
        vertices.add(halfSize);  vertices.add(halfSize);  vertices.add(halfSize); 
        vertices.add(-halfSize); vertices.add(halfSize);  vertices.add(halfSize); 
        
        vertices.add(-halfSize); vertices.add(-halfSize); vertices.add(-halfSize);
        vertices.add(-halfSize); vertices.add(halfSize);  vertices.add(-halfSize);
        vertices.add(halfSize);  vertices.add(halfSize);  vertices.add(-halfSize);
        vertices.add(halfSize);  vertices.add(-halfSize); vertices.add(-halfSize);

        List<Float> normals = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            normals.add(0.0f); normals.add(0.0f); normals.add(1.0f);
        }
        for (int i = 0; i < 4; i++) {
            normals.add(0.0f); normals.add(0.0f); normals.add(-1.0f);
        }

        List<Float> texCoords = new ArrayList<>();
        texCoords.add(0.0f); texCoords.add(0.0f);
        texCoords.add(1.0f); texCoords.add(0.0f);
        texCoords.add(1.0f); texCoords.add(1.0f);
        texCoords.add(0.0f); texCoords.add(1.0f);
        texCoords.add(0.0f); texCoords.add(0.0f);
        texCoords.add(0.0f); texCoords.add(1.0f);
        texCoords.add(1.0f); texCoords.add(1.0f);
        texCoords.add(1.0f); texCoords.add(0.0f);

        List<Integer> indices = new ArrayList<>();
        indices.add(0); indices.add(1); indices.add(2);
        indices.add(2); indices.add(3); indices.add(0);
        indices.add(4); indices.add(5); indices.add(6);
        indices.add(6); indices.add(7); indices.add(4);
        indices.add(1); indices.add(7); indices.add(6);
        indices.add(6); indices.add(2); indices.add(1);
        indices.add(4); indices.add(0); indices.add(3);
        indices.add(3); indices.add(5); indices.add(4);
        indices.add(3); indices.add(2); indices.add(6);
        indices.add(6); indices.add(5); indices.add(3);
        indices.add(4); indices.add(7); indices.add(1);
        indices.add(1); indices.add(0); indices.add(4);

        return new Mesh(vertices, normals, texCoords, indices, null);
    }
} 