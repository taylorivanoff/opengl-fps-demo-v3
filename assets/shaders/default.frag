#version 330 core

in vec3 FragPos;   // World-space fragment position
in vec3 Normal;    // World-space normal

out vec4 FragColor;

// Light properties
uniform vec3 lightDir = normalize(vec3(-0.3, -1.0, -0.5)); // Directional light
uniform vec3 lightColor = vec3(1.0, 1.0, 1.0);             // White light
uniform vec3 ambientColor = vec3(0.2, 0.2, 0.2);           // Ambient light

void main() {
    // Normalize the interpolated normal
    vec3 norm = normalize(Normal);
    
    // Compute diffuse shading (Lambertian reflection)
    float diff = max(dot(norm, -lightDir), 0.0);
    
    // Compute final color
    vec3 color = (ambientColor + diff * lightColor) * vec3(0.7, 0.7, 0.8); // Light blue-gray color
    
    FragColor = vec4(color, 1.0);
}