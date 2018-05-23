class MyImageView : ImageView {
    constructor(context: Context)
    : super(context)
    constructor(context: Context, attrs: AttributeSet)
    : super(context, attrs)
    
    var dx : Float = 0.0f
    var dy : Float = 0.0f
    
    override
    fun onTouchEvent(event: MotionEvent): Boolean {
        var handled = true
        when(event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                //Log.e("LOG","Action: ACTION_DOWN " +
                //        event.toString())
                dx = x - event.rawX
                dy = y - event.rawY
            }
            MotionEvent.ACTION_UP -> {
                //Log.e("LOG","Action: ACTION_UP " +
                //        event.toString())
            }
            MotionEvent.ACTION_MOVE -> {
                //Log.e("LOG","Action: MOVE " +
                //        event.toString())
                x = event.rawX + dx
                y = event.rawY + dy
            }
            else -> handled = false
        }
        return handled || super.onTouchEvent(event)
    }
}
