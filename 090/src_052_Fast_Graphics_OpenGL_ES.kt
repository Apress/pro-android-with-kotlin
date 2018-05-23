val vertexShaderCode = """
attribute vec4 vPosition;
attribute vec2 vTexture;
attribute vec4 vColor;

varying vec2 textureCoords;
varying vec4 fColor;

uniform mat4 uMVPMatrix;

void main() {
gl_Position = uMVPMatrix * vPosition;
textureCoords = vTexture;
fColor = vColor;
}
""".trimIndent()

val fragmentShaderCode = """
precision mediump float;
uniform sampler2D texture;  // The input texture.
varying vec2 textureCoords;
varying vec4 fColor;

void main() {
gl_FragColor = texture2D(texture, textureCoords);
// use vertex color instead:
// gl_FragColor = fColor;
}
""".trimIndent()
