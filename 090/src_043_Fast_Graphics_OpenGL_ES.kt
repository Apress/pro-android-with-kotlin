val vertexShaderCode = """
attribute vec4 vPosition;
attribute vec4 vNorm;
attribute vec4 vColor;

varying vec4 fColor;
varying vec4 fNorm;

uniform mat4 uMVPMatrix;

void main() {
gl_Position = uMVPMatrix * vPosition;
fColor = vColor;
fNorm = vNorm;
}
""".trimIndent()

val fragmentShaderCode = """
precision mediump float;
varying vec4 fColor;
varying vec4 fNorm;

void main() {
gl_FragColor = fColor;
}
""".trimIndent()
