class Square(val program: Int?, val vertBuf:Int, val idxBuf:Int) {
    val vertexBuffer: FloatBuffer
    val drawListBuffer: ShortBuffer
    
    var color = floatArrayOf(0.94f, 0.67f, 0.22f, 1.0f)
    
    companion object {
        val BYTES_PER_FLOAT = 4
        val BYTES_PER_SHORT = 2
        val COORDS_PER_VERTEX = 3
        val VERTEX_STRIDE = COORDS_PER_VERTEX *
        BYTES_PER_FLOAT
        var coords = floatArrayOf( -0.5f, 0.2f, 0.0f, // top left
        -0.5f, -0.5f, 0.0f, // bottom left
        0.2f, -0.5f, 0.0f, // bottom right
        0.2f, 0.2f, 0.0f) // top right
        val drawOrder = shortArrayOf(0, 1, 3, 2)
        // order to draw vertices
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
            
            GLES20.glBindBuffer( GLES20.GL_ARRAY_BUFFER, 0)
            GLES20.glBindBuffer( GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)
        } else {
            //TODO: error handling
        }
    }
    
    fun draw() {
        // Add program to OpenGL ES environment
        GLES20.glUseProgram(program!!)
        
        // get handle to fragment shader's vColor member
        val colorHandle = GLES20.glGetUniformLocation( program!!, "vColor")
        // Set color for drawing the square
        GLES20.glUniform4fv(colorHandle!!, 1, color, 0)
        
        // get handle to vertex shader's vPosition member
        val positionHandle = GLES20.glGetAttribLocation( program!!, "vPosition")
        
        // Enable a handle to the vertices
        GLES20.glEnableVertexAttribArray( positionHandle!!)
        
        // Prepare the coordinate data
        GLES20.glVertexAttribPointer(positionHandle!!, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, VERTEX_STRIDE, vertexBuffer)
        
        // Draw the square
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vertBuf)
        GLES20.glBindBuffer( GLES20.GL_ELEMENT_ARRAY_BUFFER, idxBuf)
        GLES20.glDrawElements(GLES20.GL_TRIANGLE_STRIP, drawListBuffer.capacity(), GLES20.GL_UNSIGNED_SHORT, 0)
        
        GLES20.glBindBuffer( GLES20.GL_ARRAY_BUFFER, 0)
        GLES20.glBindBuffer( GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)
        
        // Disable vertex array
        GLES20.glDisableVertexAttribArray( positionHandle!!)
    }
}
