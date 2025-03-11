#version 330 core

layout(location = 0) in vec3 aPosition;   // Vertex position
layout(location = 1) in vec3 aNormal;     // Vertex normal
layout(location = 2) in vec2 aTexCoord;   // Texture coordinate

out vec3 FragPos;        // Pass world position to fragment shader
out vec3 Normal;         // Pass normal vector to fragment shader
out vec2 TexCoord;       // Pass texture coordinate to fragment shader

uniform mat4 uModel;     // Model transformation matrix
uniform mat4 uView;      // View matrix (from camera)
uniform mat4 uProjection; // Projection matrix

void main() {
    FragPos = vec3(uModel * vec4(aPosition, 1.0)); // Transform position to world space
    Normal = mat3(transpose(inverse(uModel))) * aNormal; // Transform normal to world space
    TexCoord = aTexCoord; // Pass texture coordinates

    gl_Position = uProjection * uView * vec4(FragPos, 1.0); // Final position
}