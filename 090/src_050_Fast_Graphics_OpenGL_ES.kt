// The light position
val lightPosHandle = GLES20.glGetUniformLocation( program!!, "lightPos");
GLES20.glUniform4f(lightPosHandle, lightPos[0],lightPos[1],lightPos[2],lightPos[3])
