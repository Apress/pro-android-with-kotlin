class MyComplications {
    companion object {
        fun getComplicationId( pos: ComplicationConfigActivity.ComplicationLocation): Int {
            // Add supported locations here
            return when(pos) {
                ComplicationConfigActivity.ComplicationLocation.LEFT ->
                LEFT_COMPLICATION_ID
                ComplicationConfigActivity.ComplicationLocation.RIGHT ->
                RIGHT_COMPLICATION_ID
                else -> -1
            }
        }
        
        fun getSupportedComplicationTypes( complicationLocation: ComplicationConfigActivity.ComplicationLocation): IntArray? {
            return when(complicationLocation) {
                ComplicationConfigActivity.ComplicationLocation.LEFT ->
                COMPLICATION_SUPPORTED_TYPES[0]
                ComplicationConfigActivity.ComplicationLocation.RIGHT ->
                COMPLICATION_SUPPORTED_TYPES[1]
                else -> IntArray(0)
            }
        }
        
        private val LEFT_COMPLICATION_ID = 0
        private val RIGHT_COMPLICATION_ID = 1
        val COMPLICATION_IDS = intArrayOf( LEFT_COMPLICATION_ID, RIGHT_COMPLICATION_ID)
        private val complicationDrawables = SparseArray<ComplicationDrawable>()
        private val complicationDat = SparseArray<ComplicationData>()
        
        
        // Left and right dial supported types.
        private val COMPLICATION_SUPPORTED_TYPES = arrayOf( intArrayOf(ComplicationData.TYPE_RANGED_VALUE, ComplicationData.TYPE_ICON, ComplicationData.TYPE_SHORT_TEXT, ComplicationData.TYPE_SMALL_IMAGE), intArrayOf(ComplicationData.TYPE_RANGED_VALUE, ComplicationData.TYPE_ICON, ComplicationData.TYPE_SHORT_TEXT, ComplicationData.TYPE_SMALL_IMAGE) )
    }
    
    private lateinit var ctx:CanvasWatchFaceService
    private lateinit var engine:MyWatchFace.Engine
    
    fun init(ctx:CanvasWatchFaceService, engine: MyWatchFace.Engine) {
        this.ctx = ctx
        this.engine = engine
        
        // A ComplicationDrawable for each location
        val leftComplicationDrawable = ctx.getDrawable(custom_complication_styles)
        as ComplicationDrawable
        leftComplicationDrawable.setContext( ctx.applicationContext)
        val rightComplicationDrawable = ctx.getDrawable(custom_complication_styles)
        as ComplicationDrawable
        rightComplicationDrawable.setContext( ctx.applicationContext)
        
        complicationDrawables[LEFT_COMPLICATION_ID] = leftComplicationDrawable
        complicationDrawables[RIGHT_COMPLICATION_ID] = rightComplicationDrawable
        
        engine.setActiveComplications(*COMPLICATION_IDS)
    }
    
    fun onComplicationDataUpdate( complicationId: Int, complicationData: ComplicationData) {
        Log.d("LOG", "onComplicationDataUpdate() id: " + complicationId);
        complicationDat[complicationId] = complicationData
        complicationDrawables[complicationId].setComplicationData(complicationData)
        engine.invalidate()
    }
    
    fun updateComplicationBounds(width: Int, height: Int) {
        // For most Wear devices width and height
        // are the same
        val sizeOfComplication = width / 4
        val midpointOfScreen = width / 2
        
        val horizontalOffset = (midpointOfScreen - sizeOfComplication) / 2
        val verticalOffset = midpointOfScreen - sizeOfComplication / 2
        
        complicationDrawables.get(LEFT_COMPLICATION_ID).bounds = // Left, Top, Right, Bottom
        Rect( horizontalOffset, verticalOffset, horizontalOffset + sizeOfComplication, verticalOffset + sizeOfComplication)
        complicationDrawables.get(RIGHT_COMPLICATION_ID).bounds = // Left, Top, Right, Bottom
        Rect( midpointOfScreen + horizontalOffset, verticalOffset, midpointOfScreen + horizontalOffset + sizeOfComplication, verticalOffset + sizeOfComplication)
    }
    
    fun drawComplications(canvas: Canvas, drawWhen: Long) {
        COMPLICATION_IDS.forEach {
            complicationDrawables[it].draw(canvas, drawWhen)
        }
    }
    
    // Determines if tap happened inside a complication
    // area, or else returns null.
    fun getTappedComplicationId(x:Int, y:Int):Int? {
        val currentTimeMillis = System.currentTimeMillis()
        for(complicationId in MyComplications.COMPLICATION_IDS) {
            val res = complicationDat[complicationId]?.run {
                var res2 = -1
                if(isActive(currentTimeMillis) && (getType() != ComplicationData.TYPE_NOT_CONFIGURED) && (getType() != ComplicationData.TYPE_EMPTY))
                {
                    val complicationDrawable = complicationDrawables[complicationId]
                    val complicationBoundingRect = complicationDrawable.bounds
                    if (complicationBoundingRect.width() > 0) {
                        if (complicationBoundingRect.contains(x, y)) {
                            res2 = complicationId
                        }
                    } else {
                        Log.e("LOG", "Unrecognized complication id.")
                    }
                }
                res2
            } ?: -1
            if(res != -1) return res
        }
        return null
    }
    
    // The user tapped on a complication
    fun onComplicationTap(complicationId:Int) {
        Log.d("LOG", "onComplicationTap()")
        
        val complicationData = complicationDat[complicationId]
        if (complicationData != null) {
            if (complicationData.getTapAction() != null) {
                try {
                    complicationData.getTapAction().send()
                } catch (e: Exception ) {
                    Log.e("LOG", "onComplicationTap() tap error: " + e);
                }
            } else if (complicationData.getType() == ComplicationData.TYPE_NO_PERMISSION) {
                // Launch permission request.
                val componentName = ComponentName( ctx.applicationContext, MyComplications::class.java)
                val permissionRequestIntent = ComplicationHelperActivity.createPermissionRequestHelperIntent( ctx.applicationContext, componentName)
                ctx.startActivity(permissionRequestIntent)
            }
            
        } else {
            Log.d("LOG", "No PendingIntent for complication " + complicationId + ".")
        }
    }
}
