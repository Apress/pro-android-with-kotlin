WatchFaceService.TAP_TYPE_TAP -> {
    // The user has completed the tap gesture.
    // Toast.makeText(applicationContext, R.string.message, Toast.LENGTH_SHORT)
    //        .show()
    compl.getTappedComplicationId(x, y)?.run  {
        onComplicationTap(this)
    }
}
