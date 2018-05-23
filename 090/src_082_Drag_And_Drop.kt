class DragShadow(val resources: Resources, val resId:Int, view: ImageView) : View.DragShadowBuilder(view) {
    val rect = Rect()
    
    // Defines a callback that sends the drag shadow
    // dimensions and touch point back to the
    // system.
    override
    fun onProvideShadowMetrics(size: Point, touch: Point) {
        val width = view.width
        val height = view.height
        
        rect.set(0, 0, width, height)
        
        // Back to the system through the size parameter.
        size.set(width, height)
        
        // The touch point's position in the middle
        touch.set(width / 2, height / 2)
    }
    
    // Defines a callback that draws the drag shadow in a
    // Canvas
    override
    fun onDrawShadow(canvas: Canvas) {
        canvas.drawBitmap( BitmapFactory.decodeResource( resources, resId), null, rect, null)
    }
}
