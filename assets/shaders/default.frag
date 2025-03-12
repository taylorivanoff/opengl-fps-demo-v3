#version 330 core

in vec3 FragPos;
in vec3 Normal;
in vec2 TexCoord;

out vec4 FragColor;

uniform sampler2D uTexture;
uniform vec3 uViewPos;

uniform vec3 uLightPos    = vec3(10.0, 10.0, 10.0);
uniform vec3 uLightColor  = vec3(1.0, 1.0, 1.0);
uniform vec3 uAmbientColor = vec3(0.2, 0.2, 0.2);
uniform float uShininess  = 32.0;

void main() {
    vec3 norm = normalize(Normal);
    vec3 lightDir = normalize(uLightPos - FragPos);
    float diff = max(dot(norm, lightDir), 0.0);
    vec3 diffuse = diff * uLightColor;
    vec3 ambient = uAmbientColor;
    vec3 viewDir = normalize(uViewPos - FragPos);
    vec3 reflectDir = reflect(-lightDir, norm);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), 32);
    vec3 specular = spec * uLightColor;
    vec3 lighting = ambient + diffuse + specular;
    
    vec4 texColor = texture(uTexture, TexCoord);
    FragColor = vec4(lighting * texColor.rgb, texColor.a);
}