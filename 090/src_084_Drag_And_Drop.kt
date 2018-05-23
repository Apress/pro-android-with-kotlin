class MyDragListener : View.OnDragListener {
    override
    fun onDrag(v: View, event: DragEvent): Boolean {
        var res = true
        when(event.action) {
            DragEvent.ACTION_DRAG_STARTED -> {
                when(v.tag) {
                    "DragSource" -> { res = false
                        /*not a drop receiver*/ }
                    "OneTarget" -> {
                        // could visibly change
                        // possible drop receivers
                    }
                }
            }
            DragEvent.ACTION_DRAG_ENDED -> {
                when(v.tag) {
                    "OneTarget" -> {
                        // could visibly change
                        // possible drop receivers
                    }
                }
            }
            DragEvent.ACTION_DROP -> {
                when(v.tag) {
                    "OneTarget" -> {
                        // visually revert drop
                        // receiver ...
                    }
                }
                Toast.makeText(v.context, "dropped!", Toast.LENGTH_LONG).show()
            }
            DragEvent.ACTION_DRAG_ENTERED -> {
                when(v.tag) {
                    "OneTarget" -> {
                        // could visibly change
                        // possible drop receivers
                    }
                }
            }
            DragEvent.ACTION_DRAG_EXITED -> {
                when(v.tag) {
                    "OneTarget" -> {
                        // visually revert drop
                        // receiver ...
                    }
                }
            }
        }
        return res
    }
}
