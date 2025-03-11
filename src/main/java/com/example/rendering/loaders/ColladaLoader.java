package com.example.rendering.loaders;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.assimp.*;

import com.example.rendering.Mesh;
import com.example.rendering.Texture;

public class ColladaLoader {
    public static Mesh loadCollada(String filePath) {
        AIScene scene = Assimp.aiImportFile(filePath,
                Assimp.aiProcess_Triangulate | Assimp.aiProcess_GenNormals | Assimp.aiProcess_FlipUVs);

        if (scene == null) {
            throw new RuntimeException("Failed to load COLLADA file: " + filePath);
        }

        AIMesh mesh = AIMesh.create(scene.mMeshes().get(0)); // Assume first mesh

        // Load vertex positions
        List<Float> verticesList = new ArrayList<>();
        AIVector3D.Buffer vertices = mesh.mVertices();
        for (int i = 0; i < mesh.mNumVertices(); i++) {
            AIVector3D v = vertices.get(i);
            verticesList.add(v.x());
            verticesList.add(v.y());
            verticesList.add(v.z());
        }

        // Load normals
        List<Float> normalsList = new ArrayList<>();
        AIVector3D.Buffer normals = mesh.mNormals();
        if (normals != null) {
            for (int i = 0; i < mesh.mNumVertices(); i++) {
                AIVector3D n = normals.get(i);
                normalsList.add(n.x());
                normalsList.add(n.y());
                normalsList.add(n.z());
            }
        }

        // Load texture coordinates
        List<Float> textureCoordsList = new ArrayList<>();
        AIVector3D.Buffer texCoords = mesh.mTextureCoords(0);
        if (texCoords != null) {
            for (int i = 0; i < mesh.mNumVertices(); i++) {
                AIVector3D tex = texCoords.get(i);
                textureCoordsList.add(tex.x());
                textureCoordsList.add(tex.y());
            }
        }

        // Load indices
        List<Integer> indicesList = new ArrayList<>();
        AIFace.Buffer faces = mesh.mFaces();
        for (int i = 0; i < mesh.mNumFaces(); i++) {
            AIFace face = faces.get(i);
            IntBuffer indices = face.mIndices();
            while (indices.hasRemaining()) {
                indicesList.add(indices.get());
            }
        }

        // Load material texture
        Texture texture = null;
        if (scene.mMaterials() != null) {
            int materialIndex = mesh.mMaterialIndex();
            if (materialIndex >= 0) {
                AIMaterial material = AIMaterial.create(scene.mMaterials().get(materialIndex));
                texture = loadMaterialTexture(material);
            }
        }

        System.out.println("Loaded: " + filePath);

        return new Mesh(verticesList, normalsList, textureCoordsList, indicesList, texture);
    }

    private static Texture loadMaterialTexture(AIMaterial material) {
        AIString path = AIString.calloc();
        Assimp.aiGetMaterialTexture(material, Assimp.aiTextureType_DIFFUSE, 0, path,
                (IntBuffer) null, null, null, null, null, null);

        String texturePath = path.dataString();
        path.free();

        if (!texturePath.isEmpty()) {
            System.out.println("Load: " + texturePath);
            return new Texture("assets/" + texturePath);
        }

        return null;
    }
}