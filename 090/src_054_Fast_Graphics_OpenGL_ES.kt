override
fun onTouchEvent(event: MotionEvent): Boolean {
    var handled = true
    when(event.actionMasked) {
        MotionEvent.ACTION_DOWN -> {
            Log.e("LOG","Action: ACTION_DOWN " + event.toString())
        }
        MotionEvent.ACTION_UP -> {
            Log.e("LOG","Action: ACTION_UP " + event.toString())
        }
        MotionEvent.ACTION_MOVE -> {
            Log.e("LOG","Action: MOVE " + event.toString())
        }
        else -> handled = false
    }
    return handled || super.onTouchEvent(event)
}
