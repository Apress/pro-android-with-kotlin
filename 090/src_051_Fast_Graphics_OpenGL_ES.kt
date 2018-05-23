companion object {
    ...fun loadTexture(context: Context, resourceId: Int): Int {
        val textureHandle = IntArray(1)
        
        GLES20.glGenTextures(1, textureHandle, 0)
        
        if (textureHandle[0] != 0) {
            val options = BitmapFactory.Options().apply {
                inScaled = false   // No pre-scaling
            }
            
            // Read in the resource
            val bitmap = BitmapFactory.decodeResource( context.getResources(), resourceId, options)
            
            // Bind to the texture in OpenGL
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0])
            
            // Set filtering
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST)
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST)
            
            // Load the bitmap into the bound texture.
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0)
            
            // The bitmap is no longer needed.
            bitmap.recycle()
        }else{
            // TODO: handle error
        }
        return textureHandle[0]
    }
