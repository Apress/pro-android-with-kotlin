val statusShader = IntArray(1)
GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, IntBuffer.wrap(statusShader))
if (statusShader[0] == GLES20.GL_FALSE) {
    val s = GLES20.glGetShaderInfoLog(shader)
    Log.e("LOG", "Shader compilation: " + s)
}
