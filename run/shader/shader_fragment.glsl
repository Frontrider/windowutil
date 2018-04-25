#version 120

uniform sampler2D sampler;

varying vec2 tex_cords;

void main() {
    gl_FragColor = texture2D(sampler,tex_cords);
}
