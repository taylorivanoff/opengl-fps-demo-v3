package com.example.rendering.loaders;

import java.nio.IntBuffer;
import java.util.*;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.assimp.*;

import com.example.rendering.Mesh;
import com.example.rendering.Texture;
import com.example.utils.Logger;
import com.example.utils.Logger.Level;

public class ColladaSceneLoader {

    public static Map<Mesh, Vector3f> load(String filePath) {
        AIScene scene = Assimp.aiImportFile(filePath,
                Assimp.aiProcess_Triangulate | Assimp.aiProcess_GenNormals | Assimp.aiProcess_FlipUVs);

        if (scene == null) {
            throw new RuntimeException("Failed to load COLLADA file: " + filePath);
        }

        Map<Mesh, Vector3f> meshData = new HashMap<>();
        AINode rootNode = scene.mRootNode();

        processNode(rootNode, scene, new Matrix4f(), meshData);

        Logger.log(Level.INFO, "Loaded " + meshData.size() + " meshes from " + filePath);
        return meshData;
    }

    private static void processNode(AINode node, AIScene scene, Matrix4f parentTransform,
            Map<Mesh, Vector3f> meshData) {
        Matrix4f transform = convertMatrix(node.mTransformation()).mul(parentTransform);

        for (int i = 0; i < node.mNumMeshes(); i++) {
            int meshIndex = node.mMeshes().get(i);
            AIMesh mesh = AIMesh.create(scene.mMeshes().get(meshIndex));

            Vector3f position = extractPosition(transform);
            Mesh processedMesh = processMesh(mesh, scene);

            meshData.put(processedMesh, position);
        }

        for (int i = 0; i < node.mNumChildren(); i++) {
            processNode(AINode.create(node.mChildren().get(i)), scene, transform, meshData);
        }
    }

    private static Mesh processMesh(AIMesh mesh, AIScene scene) {
        List<Float> verticesList = new ArrayList<>();
        AIVector3D.Buffer vertices = mesh.mVertices();
        for (int i = 0; i < mesh.mNumVertices(); i++) {
            AIVector3D v = vertices.get(i);
            verticesList.add(v.x());
            verticesList.add(v.y());
            verticesList.add(v.z());
        }

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

        List<Float> textureCoordsList = new ArrayList<>();
        AIVector3D.Buffer texCoords = mesh.mTextureCoords(0);
        if (texCoords != null) {
            for (int i = 0; i < mesh.mNumVertices(); i++) {
                AIVector3D tex = texCoords.get(i);
                textureCoordsList.add(tex.x());
                textureCoordsList.add(tex.y());
            }
        }

        List<Integer> indicesList = new ArrayList<>();
        AIFace.Buffer faces = mesh.mFaces();
        for (int i = 0; i < mesh.mNumFaces(); i++) {
            AIFace face = faces.get(i);
            IntBuffer indices = face.mIndices();
            while (indices.hasRemaining()) {
                indicesList.add(indices.get());
            }
        }

        Texture texture = null;
        if (scene.mMaterials() != null) {
            int materialIndex = mesh.mMaterialIndex();
            if (materialIndex >= 0) {
                AIMaterial material = AIMaterial.create(scene.mMaterials().get(materialIndex));
                texture = loadMaterialTexture(material);
            }
        }

        Logger.log(Level.INFO,
                "Loaded mesh: " + mesh.mName().dataString() + " with " + verticesList.size() / 3 + " vertices.");
        return new Mesh(verticesList, normalsList, textureCoordsList, indicesList, texture);
    }

    private static Texture loadMaterialTexture(AIMaterial material) {
        AIString path = AIString.calloc();
        Assimp.aiGetMaterialTexture(material, Assimp.aiTextureType_DIFFUSE, 0, path,
                (IntBuffer) null, null, null, null, null, null);

        String texturePath = path.dataString();
        path.free();

        if (!texturePath.isEmpty()) {
            Logger.log(Level.INFO, "Texture: " + texturePath);
            return new Texture("assets/" + texturePath);
        }
        return null;
    }

    private static Matrix4f convertMatrix(AIMatrix4x4 aiMatrix) {
        return new Matrix4f(
                aiMatrix.a1(), aiMatrix.b1(), aiMatrix.c1(), aiMatrix.d1(),
                aiMatrix.a2(), aiMatrix.b2(), aiMatrix.c2(), aiMatrix.d2(),
                aiMatrix.a3(), aiMatrix.b3(), aiMatrix.c3(), aiMatrix.d3(),
                aiMatrix.a4(), aiMatrix.b4(), aiMatrix.c4(), aiMatrix.d4());
    }

    private static Vector3f extractPosition(Matrix4f transform) {
        Vector3f position = new Vector3f();
        transform.getTranslation(position);
        return position;
    }
}