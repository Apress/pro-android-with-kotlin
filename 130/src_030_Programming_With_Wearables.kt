fun createAssetFromBitmap(bitmap: Bitmap): Asset {
    val byteStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteStream)
    return Asset.createFromBytes(byteStream.toByteArray())
}
val bitmap = BitmapFactory.decodeResource( getResources(), android.R.drawable.ic_media_play)
val asset = createAssetFromBitmap(bitmap)
val dataMap = PutDataMapRequest.create("/image")
dataMap.getDataMap().putAsset("profileImage", asset)
val request = dataMap.asPutDataRequest()
val putTask: Task<DataItem> = Wearable.getDataClient(this).putDataItem(request)
