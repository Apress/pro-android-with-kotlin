class Cube(val mProgram: Int?, val vertBuf:Int, val idxBuf:Int) {
    val vertexBuffer: FloatBuffer
    val drawListBuffer: ShortBuffer
    
    companion object {
        val BYTES_PER_FLOAT = 4
        val BYTES_PER_SHORT = 2
        val COORDS_PER_VERTEX = 4
        val NORMS_PER_VERTEX = 4
        val COLORS_PER_VERTEX = 4
        val VERTEX_STRIDE = (COORDS_PER_VERTEX + NORMS_PER_VERTEX + COLORS_PER_VERTEX) * BYTES_PER_FLOAT
        var coords = floatArrayOf( // positions  + normals + colors
        // --- front
        -0.2f, -0.2f, 0.2f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.2f, -0.2f, 0.2f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.2f, 0.2f, 0.2f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, -0.2f, 0.2f, 0.2f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, // --- back
        -0.2f, -0.2f, -0.2f, 1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.2f, -0.2f, -0.2f, 1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.2f, 0.2f, -0.2f, 1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, -0.2f, 0.2f, -0.2f, 1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, // --- bottom
        -0.2f, -0.2f, 0.2f, 1.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.2f, -0.2f, 0.2f, 1.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.2f, -0.2f, -0.2f, 1.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, -0.2f, -0.2f, -0.2f, 1.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, // --- top
        -0.2f, 0.2f, 0.2f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.2f, 0.2f, 0.2f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.2f, 0.2f, -0.2f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, -0.2f, 0.2f, -0.2f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, // --- right
        0.2f, -0.2f, 0.2f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.2f, 0.2f, 0.2f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.2f, 0.2f, -0.2f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.2f, -0.2f, -0.2f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, // --- left
        -0.2f, -0.2f, 0.2f, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, -0.2f, 0.2f, 0.2f, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, -0.2f, 0.2f, -0.2f, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, -0.2f, -0.2f, -0.2f, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f )
        val drawOrder = shortArrayOf( // vertices order
        0, 1, 2,     0, 2, 3,    // front
        4, 6, 5,     4, 7, 6,    // back
        8, 10, 9,    8, 11, 10,  // bottom
        12, 13, 14,  12, 14, 15, // top
        16, 18, 17,  16, 19, 18, // right
        20, 21, 22,  20, 22, 23  // left
        )
    }
    
    init {
        // initialize vertex byte buffer for shape
        // coordinates, normals and colors
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
            GLES20.glBindBuffer( GLES20.GL_ARRAY_BUFFER, vertBuf)
            GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, vertexBuffer.capacity() * BYTES_PER_FLOAT, vertexBuffer, GLES20.GL_STATIC_DRAW)
            
            GLES20.glBindBuffer( GLES20.GL_ELEMENT_ARRAY_BUFFER, idxBuf)
            GLES20.glBufferData( GLES20.GL_ELEMENT_ARRAY_BUFFER, drawListBuffer.capacity() * BYTES_PER_SHORT, drawListBuffer, GLES20.GL_STATIC_DRAW)
            
            GLES20.glBindBuffer( GLES20.GL_ARRAY_BUFFER, 0)
            GLES20.glBindBuffer( GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)
        } else {
            // TODO: error handling
        }
    }
    
    fun draw() {
        // Add program to OpenGL ES environment
        GLES20.glUseProgram(mProgram!!)
        
        // get handle to vertex shader's vPosition member
        val mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition")
        // Enable a handle to the vertices
        GLES20.glEnableVertexAttribArray(mPositionHandle)
        // Prepare the coordinate data
        GLES20.glVertexAttribPointer( mPositionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, VERTEX_STRIDE, vertexBuffer)
        
        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // Buffer offsets are a little bit strange in the
        // Java binding - for the normals and colors we
        // create new views and then reset the vertex
        // array
        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        
        // get handle to vertex shader's vPosition member
        vertexBuffer.position(COORDS_PER_VERTEX)
        val normBuffer = vertexBuffer.slice()
        // create a new view
        vertexBuffer.rewind()
        // ... and rewind the original buffer
        val mNormHandle = GLES20.glGetAttribLocation( mProgram, "vNorm")
        if(mNormHandle >= 0) {
            // Enable a handle to the vertices
            GLES20.glEnableVertexAttribArray(mNormHandle)
            // Prepare the coordinate data
            GLES20.glVertexAttribPointer(mNormHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, VERTEX_STRIDE, normBuffer)
        }
        
        // get handle to vertex shader's vColor member
        vertexBuffer.position(COORDS_PER_VERTEX + NORMS_PER_VERTEX)
        val colorBuffer = vertexBuffer.slice()
        // create a new view
        vertexBuffer.rewind()
        // ... and rewind the original buffer
        val mColorHandle = GLES20.glGetAttribLocation( mProgram, "vColor")
        if(mColorHandle >= 0) {
            // Enable a handle to the vertices
            GLES20.glEnableVertexAttribArray(mColorHandle)
            // Prepare the coordinate data
            GLES20.glVertexAttribPointer(mColorHandle, COLORS_PER_VERTEX, GLES20.GL_FLOAT, false, VERTEX_STRIDE, colorBuffer)
        }
        
        // Draw the cube
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vertBuf)
        GLES20.glBindBuffer( GLES20.GL_ELEMENT_ARRAY_BUFFER, idxBuf)
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawListBuffer.capacity(), GLES20.GL_UNSIGNED_SHORT, 0)
        
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)
        
        // Disable attribute arrays
        GLES20.glDisableVertexAttribArray(mPositionHandle)
        if(mNormHandle >= 0)
        GLES20.glDisableVertexAttribArray(mNormHandle)
        if(mColorHandle >= 0)
        GLES20.glDisableVertexAttribArray(mColorHandle)
    }
}
