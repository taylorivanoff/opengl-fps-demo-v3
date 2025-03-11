package com.example.rendering;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Mesh {
    private final int vaoId;
    private final int vboId;
    private final int nboId;
    private final int tboId;
    private final int idxVboId;
    private final int vertexCount;
    private Texture texture;

    /**
     * Constructs a Mesh from lists of vertex and index data.
     *
     * @param vertices a list of vertex positions (x, y, z)
     * @param normals  a list of vertex normals (x, y, z) for lighting
     * @param indices  a list of indices defining triangles
     */
    public Mesh(List<Float> vertices, List<Float> normals, List<Float> textureCoords, List<Integer> indices,
            Texture texture) {
        this(convertToFloatArray(vertices), convertToFloatArray(normals), convertToFloatArray(textureCoords),
                convertToIntArray(indices), texture);
    }

    /**
     * Constructs a Mesh from arrays of vertex and index data.
     *
     * @param vertices an array of vertex positions (x, y, z)
     * @param normals  an array of vertex normals (x, y, z) for lighting
     * @param indices  an array of indices defining triangles
     */
    public Mesh(float[] vertices, float[] normals, float[] texCoords, int[] indices, Texture texture) {
        this.vertexCount = indices.length;
        this.texture = texture;

        vaoId = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoId);

        // Upload vertex position data into VBO
        vboId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
        FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
        verticesBuffer.put(vertices).flip();
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 3 * Float.BYTES, 0);
        GL20.glEnableVertexAttribArray(0);

        // Upload normal data into VBO
        nboId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, nboId);
        FloatBuffer normalsBuffer = BufferUtils.createFloatBuffer(normals.length);
        normalsBuffer.put(normals).flip();
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, normalsBuffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(1, 3, GL11.GL_FLOAT, false, 3 * Float.BYTES, 0);
        GL20.glEnableVertexAttribArray(1);

        // Upload texture coordinates
        tboId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, tboId);
        FloatBuffer texCoordsBuffer = BufferUtils.createFloatBuffer(texCoords.length);
        texCoordsBuffer.put(texCoords).flip();
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, texCoordsBuffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(2, 2, GL11.GL_FLOAT, false, 2 * Float.BYTES, 0);
        GL20.glEnableVertexAttribArray(2);

        // Upload index data into EBO
        idxVboId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, idxVboId);
        IntBuffer indicesBuffer = BufferUtils.createIntBuffer(indices.length);
        indicesBuffer.put(indices).flip();
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);

        // Unbind VBO and VAO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);
    }

    /**
     * Renders the mesh using the currently bound shader.
     */
    public void render() {
        GL30.glBindVertexArray(vaoId);

        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);

        if (texture != null) {
            texture.bind();
        }

        GL11.glDrawElements(GL11.GL_TRIANGLES, vertexCount, GL11.GL_UNSIGNED_INT, 0);

        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);

        GL30.glBindVertexArray(0);
    }

    /**
     * Cleans up the mesh's GPU resources.
     */
    public void cleanup() {
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);

        GL15.glDeleteBuffers(vboId);
        GL15.glDeleteBuffers(nboId);
        GL15.glDeleteBuffers(tboId);
        GL15.glDeleteBuffers(idxVboId);

        GL30.glDeleteVertexArrays(vaoId);

        if (texture != null) {
            texture.cleanup();
        }
    }

    // Convert List<Float> to float[]
    private static float[] convertToFloatArray(List<Float> list) {
        float[] array = new float[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    // Convert List<Integer> to int[]
    private static int[] convertToIntArray(List<Integer> list) {
        int[] array = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }
}