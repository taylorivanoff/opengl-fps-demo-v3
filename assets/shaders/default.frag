#version 330 core

in vec3 FragPos;     // World-space position of the fragment
in vec3 Normal;      // Interpolated normal for the fragment
in vec2 TexCoord;    // Interpolated texture coordinate

out vec4 FragColor;  // Final color output

uniform sampler2D uTexture;   // Texture sampler
uniform vec3 uLightPos;       // Position of the light source
uniform vec3 uViewPos;        // Camera position
uniform vec3 uLightColor;     // Light color
uniform vec3 uAmbientColor;   // Ambient light color

void main() {
    // Normalize interpolated normal
    vec3 norm = normalize(Normal);

    // Calculate lighting direction
    vec3 lightDir = normalize(uLightPos - FragPos);

    // Diffuse lighting
    float diff = max(dot(norm, lightDir), 0.0);
    vec3 diffuse = diff * uLightColor;

    // Ambient lighting
    vec3 ambient = uAmbientColor;

    // Specular lighting
    vec3 viewDir = normalize(uViewPos - FragPos);
    vec3 reflectDir = reflect(-lightDir, norm);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), 32);
    vec3 specular = spec * uLightColor;

    // Combine lighting components
    vec3 lighting = ambient + diffuse + specular;

    // Sample the texture
    vec4 texColor = texture(uTexture, TexCoord);

    // Final color output
    FragColor = vec4(lighting * texColor.rgb, texColor.a);
}