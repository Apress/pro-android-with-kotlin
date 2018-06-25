class MyGLRenderer : GLSurfaceView.Renderer {
    companion object {
        fun loadShader(type: Int, shaderCode: String)
        : Int {
            // create a vertex shader type
            //     (GLES20.GL_VERTEX_SHADER)
            // or a fragment shader type
            //      (GLES20.GL_FRAGMENT_SHADER)
            val shader = GLES20.glCreateShader(type)
            
            // add the source code to the shader and
            // compile it
            GLES20.glShaderSource(shader, shaderCode)
            GLES20.glCompileShader(shader)
            
            return shader
        }
    }
    
    val vertexShaderCode = """
    attribute vec4 vPosition;
    uniform mat4 uMVPMatrix;
    void main() {
    gl_Position = uMVPMatrix * vPosition;
    }
    """.trimIndent()
    
    val fragmentShaderCode = """
    precision mediump float;
    uniform vec4 vColor;
    void main() {
    gl_FragColor = vColor;
    }
    """.trimIndent()
    
    var triangle:Triangle? = null
    var square:Square? = null
    var program:Int? = 0
    
    val vbo = IntArray(2) // vertex buffers
    val ibo = IntArray(2) // index buffers
    
    val vMatrix:FloatArray = FloatArray(16)
    val projMatrix:FloatArray = FloatArray(16)
    val mvpMatrix:FloatArray = FloatArray(16)
    
    // Called once to set up the view's
    // OpenGL ES environment.
    override fun onSurfaceCreated(gl: GL10?, config: javax.microedition.khronos.egl.EGLConfig?) {
        // enable face culling feature
        GLES20.glEnable(GL10.GL_CULL_FACE)
        // specify which faces to not draw
        GLES20.glCullFace(GL10.GL_BACK)
        
        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
        
        val vertexShader = loadShader( GLES20.GL_VERTEX_SHADER, vertexShaderCode)
        val fragmentShader = loadShader( GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode)
        
        // create empty OpenGL ES Program
        program = GLES20.glCreateProgram()
        
        // add the vertex shader to program
        GLES20.glAttachShader(program!!, vertexShader)
        
        // add the fragment shader to program
        GLES20.glAttachShader(program!!, fragmentShader)
        
        // creates OpenGL ES program executables
        GLES20.glLinkProgram(program!!)
        
        GLES20.glGenBuffers(2, vbo, 0) // just buffer names
        GLES20.glGenBuffers(2, ibo, 0)
        
        // Create a camera view and an orthogonal projection
        // matrix
        Matrix.setLookAtM(vMatrix, 0, 0f, 0f, 3.0f, 0f, 0f, 0f, 0f, 1.0f, 0.0f)
        Matrix.orthoM(projMatrix,0,-1.0f,1.0f, -1.0f, 1.0f, 100.0f, -100.0f)
    }
    
    // Called for each redraw of the view.
    // If renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY
    // (see MyGLSurfaceView)
    // this will not be called every frame
    override fun onDrawFrame(unused: GL10) {
        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        
        GLES20.glUseProgram(program!!)
        val muMVPMatrixHandle = GLES20.glGetUniformLocation( program!!, "uMVPMatrix");
        Matrix.multiplyMM(mvpMatrix, 0, projMatrix, 0, vMatrix, 0)
        
        // Apply the combined projection and camera view
        // transformations
        GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, mvpMatrix, 0);
        
        triangle = triangle ?: Triangle(program,vbo[0],ibo[0])
        triangle?.draw()
        
        square = square ?: Square(program,vbo[1],ibo[1])
        square?.draw()
    }
    
    override
    fun onSurfaceChanged(unused: GL10, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
    }
}
