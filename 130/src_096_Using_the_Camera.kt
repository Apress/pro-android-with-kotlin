/**
* A custom TextureView which is able to automatically
* crop its size according to an aspect ratio set
*/
class AutoFitTextureView : TextureView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, attributeSetId: Int) : super(context, attrs, attributeSetId)
    
    var mRatioWidth = 0
    var mRatioHeight = 0
    
    /**
    * Sets the aspect ratio for this view. The size of
    * the view will be measured based on the ratio
    * calculated from the parameters. Note that the
    * actual sizes of parameters don't matter, that
    * is, calling setAspectRatio(2, 3) and
    * setAspectRatio(4, 6) make the same result.
    *
    * @param width  Relative horizontal size
    * @param height Relative vertical size
    */
    fun setAspectRatio(width:Int, height:Int) {
        if (width < 0 || height < 0) {
            throw IllegalArgumentException( "Size cannot be negative.");
        }
        mRatioWidth = width;
        mRatioHeight = height;
        requestLayout()
    }
    
    override
    fun onMeasure(widthMeasureSpec:Int, heightMeasureSpec:Int) {
        super.onMeasure( widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        if (0 == mRatioWidth || 0 == mRatioHeight) {
            setMeasuredDimension(width, height)
        } else {
            val ratio = 1.0 * mRatioWidth / mRatioHeight
            if (width < height * ratio) {
                setMeasuredDimension( width, (width / ratio).toInt())
            } else {
                setMeasuredDimension( (height * ratio).toInt(), height)
            }
        }
    }
}
