import android.app.ActivityManager
import android.content.Context
import android.opengl.GLSurfaceView
import android.util.Log
import javax.microedition.khronos.egl.EGL10

class MyGLSurfaceView(context: Context) : GLSurfaceView(context) {
    val renderer: MyGLRenderer
    var supports3x = false
    var minVers = 0
    
    init {
        fetchVersion()
        
        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2)
        
        // We set the 2.x context factory to use for
        // the view
        setEGLContextFactory()
        
        // We set the renderer for drawing the graphics
        renderer = MyGLRenderer()
        setRenderer(renderer)
        
        // This setting prevents the GLSurfaceView frame
        // from being redrawn until you call
        // requestRender()
        renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY
    }
    
    private fun fetchVersion() {
        val activityManager = context.getSystemService( Context.ACTIVITY_SERVICE)
        as ActivityManager
        val configurationInfo = activityManager.deviceConfigurationInfo
        val vers = configurationInfo.glEsVersion
        // e.g. "2.0"
        supports3x = vers.split(".")[0] == "3"
        minVers = vers.split(".")[1].toInt()
        Log.i("LOG", "Supports OpenGL 3.x = " + supports3x)
        Log.i("LOG", "OpenGL minor version = " + minVers)
    }
    
    private fun setEGLContextFactory() {
        val EGL_CONTEXT_CLIENT_VERSION = 0x3098
        // from egl.h c-source
        class ContextFactory : GLSurfaceView.EGLContextFactory {
            override fun createContext(egl: EGL10, display: javax.microedition.khronos.egl.EGLDisplay?, eglConfig: javax.microedition.khronos.egl.EGLConfig?)
            :javax.microedition.khronos.egl.EGLContext? {
                val attrib_list = intArrayOf(EGL_CONTEXT_CLIENT_VERSION, 2, EGL10.EGL_NONE)
                val ectx = egl.eglCreateContext(display, eglConfig, EGL10.EGL_NO_CONTEXT, attrib_list)
                return ectx
            }
            
            override fun destroyContext(egl: EGL10, display: javax.microedition.khronos.egl.EGLDisplay?, context: javax.microedition.khronos.egl.EGLContext?) {
                egl.eglDestroyContext(display, context)
            }
        }
        setEGLContextFactory(ContextFactory())
    }
}
