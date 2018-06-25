class Triangle {
    val vertexShaderCode = """
    attribute vec4 vPosition;
    void main() {
    gl_Position = vPosition;
    }
    """.trimIndent()
    
    val fragmentShaderCode = """
    precision mediump float;
    uniform vec4 vColor;
    void main() {
    gl_FragColor = vColor;
    }
    """.trimIndent()
    
    var program:Int? = 0
    
    val vertexBuffer: FloatBuffer
    
    var color = floatArrayOf(0.6f, 0.77f, 0.22f, 1.0f)
    
    var positionHandle: Int? = 0
    var colorHandle: Int? = 0
    
    val vertexCount = triangleCoords.size / COORDS_PER_VERTEX
    val vertexStride = COORDS_PER_VERTEX * 4
    // 4 bytes per vertex
    
    companion object {
        // number of coordinates per vertex
        internal val COORDS_PER_VERTEX = 3
        internal var triangleCoords = floatArrayOf( // in counterclockwise order:
        0.0f, 0.6f, 0.0f,   // top
        -0.5f, -0.3f, 0.0f, // bottom left
        0.5f, -0.3f, 0.0f   // bottom right
        )
    }
    
    init {
        val vertexShader = MyGLRenderer.loadShader( GLES20.GL_VERTEX_SHADER, vertexShaderCode)
        val fragmentShader = MyGLRenderer.loadShader( GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode)
        
        // create empty OpenGL ES Program
        program = GLES20.glCreateProgram()
        
        // add the vertex shader to program
        GLES20.glAttachShader(program!!, vertexShader)
        
        // add the fragment shader to program
        GLES20.glAttachShader(program!!, fragmentShader)
        
        // creates OpenGL ES program executables
        GLES20.glLinkProgram(program!!)
        
        
        // initialize vertex byte buffer for shape
        // coordinates
        val bb = ByteBuffer.allocateDirect( // (4 bytes per float)
        triangleCoords.size * 4)
        // use the device hardware's native byte order
        bb.order(ByteOrder.nativeOrder())
        
        // create a floating point buffer from bb
        vertexBuffer = bb.asFloatBuffer()
        // add the coordinates to the buffer
        vertexBuffer.put(triangleCoords)
        // set the buffer to start at 0
        vertexBuffer.position(0)
    }
    
    fun draw() {
        // Add program to OpenGL ES environment
        GLES20.glUseProgram(program!!)
        
        // get handle to vertex shader's vPosition member
        positionHandle = GLES20.glGetAttribLocation( program!!, "vPosition")
        
        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray( positionHandle!!)
        
        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(positionHandle!!, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer)
        
        // get handle to fragment shader's vColor member
        colorHandle = GLES20.glGetUniformLocation( program!!, "vColor")
        
        // Set color for drawing the triangle
        GLES20.glUniform4fv(colorHandle!!, 1, color, 0)
        
        // Draw the triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount)
        
        // Disable vertex array
        GLES20.glDisableVertexAttribArray( positionHandle!!)
    }
}
