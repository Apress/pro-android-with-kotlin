theView.setOnTouchListener { view,event ->
    fun actionToString(action:Int) : String = mapOf( MotionEvent.ACTION_DOWN to "Down", MotionEvent.ACTION_MOVE to "Move", MotionEvent.ACTION_POINTER_DOWN to "Pointer Down", MotionEvent.ACTION_UP to "Up", MotionEvent.ACTION_POINTER_UP to "Pointer Up", MotionEvent.ACTION_OUTSIDE to "Outside", MotionEvent.ACTION_CANCEL to "Cancel").getOrDefault(action,"")
    
    val action = event.actionMasked
    val index = event.actionIndex
    var xPos = -1
    var yPos = -1
    Log.d("LOG", "The action is " + actionToString(action))
    
    if (event.pointerCount > 1) {
        Log.d("LOG", "Multitouch event")
        // The coordinates of the current screen contact,
        // relative to the responding View or Activity.
        xPos = event.getX(index).toInt()
        yPos = event.getY(index).toInt()
    } else {
        // Single touch event
        Log.d("LOG", "Single touch event")
        xPos = event.getX(index).toInt()
        yPos = event.getY(index).toInt()
    }
    
    // do more things...
    
    true
}
