theView.setOnTouchListener { view, event ->
    if(event.action == MotionEvent.ACTION_DOWN) {
        val shadow = DragShadow(resources, R.the_dragging_image, theView)
        
        val item = ClipData.Item(frog.tag.toString())
        val dragData = ClipData(frog.tag.toString(), arrayOf(MIMETYPE_TEXT_PLAIN), item)
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            theView.startDragAndDrop(dragData, shadow, null, 0)
        } else {
            theView.startDrag(dragData, shadow, null, 0)
        }
    }
    true
}
