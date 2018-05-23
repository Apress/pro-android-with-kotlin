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
    
    var triangle:Triangle? = null
    var quad:Quad? = null
    
    // Called once to set up the view's OpenGL ES
    // environment.
    override
    fun onSurfaceCreated(gl: GL10?, config: javax.microedition.khronos.egl.EGLConfig?) {
        // enable face culling feature
        GLES20.glEnable(GL10.GL_CULL_FACE)
        // specify which faces to not draw
        GLES20.glCullFace(GL10.GL_BACK)
        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
    }
    
    // Called for each redraw of the view.
    // If renderMode =
    //     GLSurfaceView.RENDERMODE_WHEN_DIRTY
    // (see MyGLSurfaceView)
    // this will not be called every frame
    override
    fun onDrawFrame(unused: GL10) {
        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        
        triangle = triangle ?: Triangle()
        triangle?.draw()
        
        quad = quad ?: Quad()
        quad?.draw()
    }
    
    override
    fun onSurfaceChanged(unused: GL10, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
    }
}
