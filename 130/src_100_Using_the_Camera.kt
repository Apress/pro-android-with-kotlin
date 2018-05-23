class MainActivity : AppCompatActivity() {
    companion object {
        val LOG_KEY = "main"
        val PERM_REQUEST_CAMERA = 642
    }
    
    lateinit var previewDim:PreviewDimension
    lateinit var camera:Camera
    
    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        val permission1 = ContextCompat.checkSelfPermission( this, Manifest.permission.CAMERA)
        if (permission1 != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), PERM_REQUEST_CAMERA)
        }else{
            start()
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        camera.close()
    }
    
    fun go(v: View) {
        camera.takePicture()
    }
    
    private fun start() {
        previewDim = PreviewDimension()
        camera = Camera( this, previewDim, cameraTexture).apply {
            addPreviewSizeListener { w,h ->
                Log.e(LOG_KEY, "Preview size by PreviewSizeListener: ${w} ${h}")
                cameraTexture.setAspectRatio(w,h)
            }
            addStillImageConsumer(::dataArrived)
        }
        
        // When the screen is turned off and turned back
        // on, the SurfaceTexture is already available,
        // and "onSurfaceTextureAvailable" will not be
        // called. In that case, we can open a camera
        // and start preview from here (otherwise, we
        // wait until the surface is ready in the
        // SurfaceTextureListener).
        if (cameraTexture.isAvailable()) {
            camera.openCamera(cameraTexture.width, cameraTexture.height)
            configureTransform(cameraTexture.width, cameraTexture.height)
        } else {
            cameraTexture.surfaceTextureListener = object : TextureView.SurfaceTextureListener {
                override
                fun onSurfaceTextureSizeChanged( surface: SurfaceTexture?, width: Int, height: Int) {
                    configureTransform(width, height)
                }
                override
                fun onSurfaceTextureUpdated( surface: SurfaceTexture?) {
                }
                override
                fun onSurfaceTextureDestroyed( surface: SurfaceTexture?): Boolean {
                    return true
                }
                override
                fun onSurfaceTextureAvailable( surface: SurfaceTexture?, width: Int, height: Int) {
                    camera.openCamera(width, height)
                    configureTransform(width, height)
                }
            }
        }
    }
    
    private fun dataArrived(it: ByteArray) {
        Log.e(LOG_KEY, "Data arrived: " + it.size)
        // do more with the picture...
    }
    
    private fun configureTransform( viewWidth: Int, viewHeight: Int) {
        val matrix = previewDim.getTransformationMatrix( this, viewWidth, viewHeight)
        cameraTexture.setTransform(matrix)
    }
    
    override
    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERM_REQUEST_CAMERA -> {
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    start()
                }
            }
        }
    }
}
