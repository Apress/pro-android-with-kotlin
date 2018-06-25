class Quad {
    
    val vertexBuffer: FloatBuffer
    val drawListBuffer: ShortBuffer
    
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
    
    // The shader program
    var program:Int? = 0
    
    var color = floatArrayOf(0.94f, 0.67f, 0.22f, 1.0f)
    
    val vbo = IntArray(1) // one vertex buffer
    val ibo = IntArray(1) // one index buffer
    
    var positionHandle: Int? = 0
    var colorHandle: Int? = 0
    
    companion object {
        val BYTES_PER_FLOAT = 4
        val BYTES_PER_SHORT = 2
        val COORDS_PER_VERTEX = 3
        val VERTEX_STRIDE = COORDS_PER_VERTEX *
        BYTES_PER_FLOAT
        var quadCoords = floatArrayOf( -0.5f, 0.2f, 0.0f, // top left
        -0.5f, -0.5f, 0.0f, // bottom left
        0.2f, -0.5f, 0.0f, // bottom right
        0.2f, 0.2f, 0.0f) // top right
        val drawOrder = shortArrayOf(0, 1, 3, 2)
        // order to draw vertices
    }
    
    init {
        val vertexShader = MyGLRenderer.loadShader( GLES20.GL_VERTEX_SHADER, vertexShaderCode)
        val fragmentShader = MyGLRenderer.loadShader( GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode)
        
        program = GLES20.glCreateProgram().apply {
            // add the vertex shader to program
            GLES20.glAttachShader(this, vertexShader)
            
            // add the fragment shader to program
            GLES20.glAttachShader(this, fragmentShader)
            
            // creates OpenGL ES program executables
            GLES20.glLinkProgram(this)
        }
        
        // initialize vertex byte buffer for shape coords
        vertexBuffer = ByteBuffer.allocateDirect( quadCoords.size * BYTES_PER_FLOAT).apply{
            order(ByteOrder.nativeOrder())
        }.asFloatBuffer().apply {
            put(quadCoords)
            position(0)
        }
        
        // initialize byte buffer for the draw list
        drawListBuffer = ByteBuffer.allocateDirect( drawOrder.size * BYTES_PER_SHORT).apply {
            order(ByteOrder.nativeOrder())
        }.asShortBuffer().apply {
            put(drawOrder)
            position(0)
        }
        
        GLES20.glGenBuffers(1, vbo, 0);
        GLES20.glGenBuffers(1, ibo, 0);
        if (vbo[0] > 0 && ibo[0] > 0) {
            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbo[0])
            GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, vertexBuffer.capacity() * BYTES_PER_FLOAT, vertexBuffer, GLES20.GL_STATIC_DRAW)
            
            GLES20.glBindBuffer( GLES20.GL_ELEMENT_ARRAY_BUFFER, ibo[0])
            GLES20.glBufferData( GLES20.GL_ELEMENT_ARRAY_BUFFER, drawListBuffer.capacity() * BYTES_PER_SHORT, drawListBuffer, GLES20.GL_STATIC_DRAW)
            
            GLES20.glBindBuffer( GLES20.GL_ARRAY_BUFFER, 0);
            GLES20.glBindBuffer( GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)
        } else {
            //TODO: some error handling
        }
    }
    
    fun draw() {
        // Add program to OpenGL ES environment
        GLES20.glUseProgram(program!!)
        
        // Get handle to fragment shader's vColor member
        colorHandle = GLES20.glGetUniformLocation( program!!, "vColor")
        // Set color for drawing the quad
        GLES20.glUniform4fv(colorHandle!!, 1, color, 0)
        
        // Get handle to vertex shader's vPosition member
        positionHandle = GLES20.glGetAttribLocation( program!!, "vPosition")
        
        // Enable a handle to the vertices
        GLES20.glEnableVertexAttribArray( positionHandle!!)
        
        // Prepare the coordinate data
        GLES20.glVertexAttribPointer(positionHandle!!, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, VERTEX_STRIDE, vertexBuffer)
        
        // Draw the quad
        GLES20.glBindBuffer( GLES20.GL_ARRAY_BUFFER, vbo[0]);
        
        // Bind Attributes
        GLES20.glBindBuffer( GLES20.GL_ELEMENT_ARRAY_BUFFER, ibo[0])
        GLES20.glDrawElements(GLES20.GL_TRIANGLE_STRIP, drawListBuffer.capacity(), GLES20.GL_UNSIGNED_SHORT, 0)
        
        GLES20.glBindBuffer( GLES20.GL_ARRAY_BUFFER, 0)
        GLES20.glBindBuffer( GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)
        
        // Disable vertex array
        GLES20.glDisableVertexAttribArray( positionHandle!!)
    }
}
