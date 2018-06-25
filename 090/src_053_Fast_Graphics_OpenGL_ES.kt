class Plane(val program: Int?, val vertBuf:Int, val idxBuf:Int, val context: Context) {
    
    val vertexBuffer: FloatBuffer
    val drawListBuffer: ShortBuffer
    
    // Used to pass in the texture.
    var textureUniformHandle: Int = 0
    // A handle to our texture data
    var textureDataHandle: Int = 0
    
    companion object {
        val BYTES_PER_FLOAT = 4
        val BYTES_PER_SHORT = 2
        val COORDS_PER_VERTEX = 4
        val TEXTURE_PER_VERTEX = 2
        val NORMS_PER_VERTEX = 4
        val COLORS_PER_VERTEX = 4
        val VERTEX_STRIDE = (COORDS_PER_VERTEX + TEXTURE_PER_VERTEX + NORMS_PER_VERTEX + COLORS_PER_VERTEX) * BYTES_PER_FLOAT
        var coords = floatArrayOf( // positions, normals, texture, colors
        -0.2f, -0.2f, 0.2f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.2f, -0.2f, 0.2f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.2f, 0.2f, 0.2f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, -0.2f, 0.2f, 0.2f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f )
        val drawOrder = shortArrayOf( // vertices order
        0, 1, 2,     0, 2, 3 )
    }
    
    init {
        // initialize vertex byte buffer for shape
        // coordinates
        vertexBuffer = ByteBuffer.allocateDirect( coords.size * BYTES_PER_FLOAT).apply{
            order(ByteOrder.nativeOrder())
        }.asFloatBuffer().apply {
            put(coords)
            position(0)
        }
        
        // initialize byte buffer for the draw list
        drawListBuffer = ByteBuffer.allocateDirect( drawOrder.size * BYTES_PER_SHORT).apply {
            order(ByteOrder.nativeOrder())
        }.asShortBuffer().apply {
            put(drawOrder)
            position(0)
        }
        
        if (vertBuf > 0 && idxBuf > 0) {
            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vertBuf)
            GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, vertexBuffer.capacity() * BYTES_PER_FLOAT, vertexBuffer, GLES20.GL_STATIC_DRAW)
            
            GLES20.glBindBuffer( GLES20.GL_ELEMENT_ARRAY_BUFFER, idxBuf)
            GLES20.glBufferData( GLES20.GL_ELEMENT_ARRAY_BUFFER, drawListBuffer.capacity() * BYTES_PER_SHORT, drawListBuffer, GLES20.GL_STATIC_DRAW)
            
            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)
            GLES20.glBindBuffer( GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)
        } else {
            // TODO: handle error
        }
        
        // Load the texture
        textureDataHandle = MyGLRenderer.loadTexture(context, R.drawable.myImage)
    }
    
    fun draw() {
        // Add program to OpenGL ES environment
        GLES20.glUseProgram(program!!)
        
        // get handle to vertex shader's vPosition member
        val positionHandle = GLES20.glGetAttribLocation(program, "vPosition")
        // Enable a handle to the vertices
        GLES20.glEnableVertexAttribArray(positionHandle)
        // Prepare the coordinate data
        GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, VERTEX_STRIDE, vertexBuffer)
        
        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // Buffer offsets are a little bit strange in the
        // Java binding - For the other arrays we create
        // a new view and then reset the vertex array
        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        
        // get handle to vertex shader's vNorm member
        vertexBuffer.position(COORDS_PER_VERTEX)
        val normBuffer = vertexBuffer.slice()
        // create a new view
        vertexBuffer.rewind()
        // ... and rewind the original buffer
        val normHandle = GLES20.glGetAttribLocation(program, "vNorm")
        if(normHandle >= 0) {
            // Enable a handle to the vertices
            GLES20.glEnableVertexAttribArray(normHandle)
            // Prepare the coordinate data
            GLES20.glVertexAttribPointer(normHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, VERTEX_STRIDE, normBuffer)
        }
        
        // get handle to vertex shader's textureCoords
        vertexBuffer.position(COORDS_PER_VERTEX + NORMS_PER_VERTEX)
        val textureBuffer = vertexBuffer.slice()
        // create a new view
        vertexBuffer.rewind()
        // ... and rewind the original buffer
        val textureHandle = GLES20.glGetAttribLocation(program, "vTexture")
        if(textureHandle >= 0) {
            // Enable a handle to the texture coords
            GLES20.glEnableVertexAttribArray( textureHandle)
            // Prepare the coordinate data
            GLES20.glVertexAttribPointer(textureHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, VERTEX_STRIDE, textureBuffer)
        }
        
        // get handle to vertex shader's vColor member
        vertexBuffer.position(COORDS_PER_VERTEX + NORMS_PER_VERTEX + TEXTURE_PER_VERTEX)
        val colorBuffer = vertexBuffer.slice()
        // create a new view
        vertexBuffer.rewind()
        // ... and rewind the original buffer
        val colorHandle = GLES20.glGetAttribLocation(program, "vColor")
        if(colorHandle >= 0) {
            // Enable a handle to the vertices
            GLES20.glEnableVertexAttribArray(colorHandle)
            // Prepare the coordinate data
            GLES20.glVertexAttribPointer(colorHandle, COLORS_PER_VERTEX, GLES20.GL_FLOAT, false, VERTEX_STRIDE, colorBuffer)
        }
        
        textureUniformHandle = GLES20.glGetUniformLocation(program, "texture")
        if(textureHandle >= 0) {
            // Set the active texture unit to
            // texture unit 0.
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
            // Tell the texture uniform sampler to use
            // this texture in the shader by binding to
            // texture unit 0.
            GLES20.glUniform1i(textureUniformHandle, 0)
        }
        
        // Draw the plane
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vertBuf)
        GLES20.glBindBuffer( GLES20.GL_ELEMENT_ARRAY_BUFFER, idxBuf)
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawListBuffer.capacity(), GLES20.GL_UNSIGNED_SHORT, 0)
        
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)
        GLES20.glBindBuffer( GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)
        
        // Disable vertex array
        GLES20.glDisableVertexAttribArray( positionHandle)
        if(normHandle >= 0)
        GLES20.glDisableVertexAttribArray( normHandle)
        if(textureHandle >= 0)
        GLES20.glDisableVertexAttribArray( textureHandle)
        if(colorHandle >= 0)
        GLES20.glDisableVertexAttribArray( colorHandle)
    }
}
