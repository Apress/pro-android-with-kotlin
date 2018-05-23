val vertexShaderCode = """
attribute vec4 vPosition;
attribute vec4 vNorm;
attribute vec4 vColor;

varying vec4 fColor;
varying vec3 N;
varying vec3 v;

uniform mat4 uVMatrix;
uniform mat4 uMVPMatrix;

void main() {
gl_Position = uMVPMatrix * vPosition;
fColor = vColor;
v = vec3(uVMatrix * vPosition);
N = normalize(vec3(uVMatrix * vNorm));
}
""".trimIndent()
