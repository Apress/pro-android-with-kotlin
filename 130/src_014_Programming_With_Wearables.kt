class ComplicationConfigActivity : Activity(), View.OnClickListener {
    companion object {
        val TAG = "LOG"
        val COMPLICATION_CONFIG_REQUEST_CODE = 1001
    }
    
    var mLeftComplicationId: Int = 0
    var mRightComplicationId: Int = 0
    var mSelectedComplicationId: Int = 0
    
    // Used to identify a specific service that renders
    // the watch face.
    var mWatchFaceComponentName: ComponentName? = null
    
    // Required to retrieve complication data from watch
    // face for preview.
    var mProviderInfoRetriever: ProviderInfoRetriever? = null
    
    var mLeftComplicationBackground: ImageView? = null
    var mRightComplicationBackground: ImageView? = null
    
    var mLeftComplication: ImageButton? = null
    var mRightComplication: ImageButton? = null
    
    var mDefaultAddComplicationDrawable: Drawable? = null
    
    enum class ComplicationLocation {
        LEFT, RIGHT
    }
    
    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContentView(R.layout.activity_config)
        
        mDefaultAddComplicationDrawable = getDrawable(R.drawable.add_complication)
        
        mSelectedComplicationId = -1
        
        mLeftComplicationId = MyComplications.getComplicationId( ComplicationLocation.LEFT)
        mRightComplicationId = MyComplications.getComplicationId( ComplicationLocation.RIGHT)
        
        mWatchFaceComponentName = ComponentName(applicationContext, MyWatchFace::class.java!!)
        
        // Sets up left complication preview.
        mLeftComplicationBackground = left_complication_background
        mLeftComplication = left_complication
        mLeftComplication!!.setOnClickListener(this)
        
        // Sets default as "Add Complication" icon.
        mLeftComplication!!.setImageDrawable( mDefaultAddComplicationDrawable)
        mLeftComplicationBackground!!.setVisibility( View.INVISIBLE)
        
        // Sets up right complication preview.
        mRightComplicationBackground = right_complication_background
        mRightComplication = right_complication
        mRightComplication!!.setOnClickListener(this)
        
        // Sets default as "Add Complication" icon.
        mRightComplication!!.setImageDrawable( mDefaultAddComplicationDrawable)
        mRightComplicationBackground!!.setVisibility( View.INVISIBLE)
        
        mProviderInfoRetriever = ProviderInfoRetriever(applicationContext, Executors.newCachedThreadPool())
        mProviderInfoRetriever!!.init()
        
        retrieveInitialComplicationsData()
    }
    
    override fun onDestroy() {
        super.onDestroy()
        mProviderInfoRetriever!!.release()
    }
    
    fun retrieveInitialComplicationsData() {
        val complicationIds = MyComplications.COMPLICATION_IDS
        mProviderInfoRetriever!!.retrieveProviderInfo( object : ProviderInfoRetriever.OnProviderInfoReceivedCallback() {
            override fun onProviderInfoReceived( watchFaceComplicationId: Int, complicationProviderInfo: ComplicationProviderInfo?)
            {
                Log.d(TAG, "onProviderInfoReceived: " + complicationProviderInfo)
                updateComplicationViews( watchFaceComplicationId, complicationProviderInfo)
            }
        }, mWatchFaceComponentName, *complicationIds)
    }
    
    override
    fun onClick(view: View) {
        if (view.equals(mLeftComplication)) {
            Log.d(TAG, "Left Complication click()")
            launchComplicationHelperActivity(
            ComplicationLocation.LEFT)
        } else if (view.equals(mRightComplication)) {
            Log.d(TAG, "Right Complication click()")
            launchComplicationHelperActivity(
            ComplicationLocation.RIGHT)
        }
    }
    
    fun launchComplicationHelperActivity(
    complicationLocation: ComplicationLocation) {
        
        mSelectedComplicationId = MyComplications.getComplicationId(
        complicationLocation)
        
        if (mSelectedComplicationId >= 0) {
            val supportedTypes = MyComplications.getSupportedComplicationTypes(
            complicationLocation)!!
            
            startActivityForResult(
            ComplicationHelperActivity.createProviderChooserHelperIntent( applicationContext, mWatchFaceComponentName, mSelectedComplicationId, *supportedTypes), ComplicationConfigActivity.COMPLICATION_CONFIG_REQUEST_CODE)
        } else {
            Log.d(TAG, "Complication not supported by watch face.")
        }
    }
    
    fun updateComplicationViews(
    watchFaceComplicationId: Int, complicationProviderInfo: ComplicationProviderInfo?)
    {
        Log.d(TAG, "updateComplicationViews(): id: "+ watchFaceComplicationId)
        Log.d(TAG, "\tinfo: " + complicationProviderInfo)
        
        if (watchFaceComplicationId == mLeftComplicationId) {
            if (complicationProviderInfo != null) {
                mLeftComplication!!.setImageIcon(
                complicationProviderInfo.providerIcon)
                mLeftComplicationBackground!!.setVisibility(View.VISIBLE)
            } else {
                mLeftComplication!!.setImageDrawable(
                mDefaultAddComplicationDrawable)
                mLeftComplicationBackground!!.setVisibility(View.INVISIBLE)
            }
            
        } else if (watchFaceComplicationId == mRightComplicationId) {
            if (complicationProviderInfo != null) {
                mRightComplication!!.setImageIcon(
                complicationProviderInfo.providerIcon)
                mRightComplicationBackground!!.setVisibility(View.VISIBLE)
                
            } else {
                mRightComplication!!.setImageDrawable(
                mDefaultAddComplicationDrawable)
                mRightComplicationBackground!!.setVisibility(View.INVISIBLE)
            }
        }
    }
    
    override
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == COMPLICATION_CONFIG_REQUEST_CODE
        && resultCode == Activity.RESULT_OK) {
            
            // Retrieves information for selected
            //  Complication provider.
            val complicationProviderInfo = data.getParcelableExtra<
            ComplicationProviderInfo>(
            ProviderChooserIntent.EXTRA_PROVIDER_INFO)
            Log.d(TAG, "Provider: " + complicationProviderInfo)
            
            if (mSelectedComplicationId >= 0) {
                updateComplicationViews(
                mSelectedComplicationId, complicationProviderInfo)
            }
        }
    }
}
