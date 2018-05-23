val statusShader = IntArray(1)
GLES20.glGetShaderiv(mProgram!!, GLES20.GL_LINK_STATUS, IntBuffer.wrap(statusShader))
if (statusShader[0] == GLES20.GL_FALSE) {
    val s = GLES20.glGetShaderInfoLog(mProgram!!)
    Log.e("LOG", "Shader linking: " + s)
}
