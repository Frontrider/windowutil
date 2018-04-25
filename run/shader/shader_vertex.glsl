#version 120

attribute vec3 verticies;
attribute vec2 textures;

varying vec2 tex_cords;


uniform mat4 projection;

void main() {
    tex_cords = textures;
    gl_Position = projection * vec4(verticies,1);
}
