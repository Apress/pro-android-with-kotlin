lateinit var compl : MyComplications

private fun initializeComplications() {
    compl = MyComplications()
    compl.init(this@MyWatchFace, this)
}

override
fun onComplicationDataUpdate( complicationId: Int, complicationData: ComplicationData)
{
    compl.onComplicationDataUpdate( complicationId,complicationData)
}

private fun drawComplications( canvas: Canvas, drawWhen: Long) {
    compl.drawComplications(canvas, drawWhen)
}

// Fires PendingIntent associated with
// complication (if it has one).
private fun onComplicationTap( complicationId:Int) {
    Log.d("LOG", "onComplicationTap()")
    compl.onComplicationTap(complicationId)
}
