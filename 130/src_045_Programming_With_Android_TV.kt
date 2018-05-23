val builder = Channel.Builder()

// Intent to execute when the App link gets tapped.
val appLink = Intent(...).toUri(Intent.URI_INTENT_SCHEME)

// You must use type `TYPE_PREVIEW`
builder.setType(TvContractCompat.Channels.TYPE_PREVIEW)
.setDisplayName("Channel Name")
.setAppLinkIntentUri(Uri.parse(appLink))
val channel = builder.build()
val channelUri = contentResolver.insert( TvContractCompat.Channels.CONTENT_URI, channel.toContentValues())

val channelId = ContentUris.parseId(channelUri)
// Choose one or the other
ChannelLogoUtils.storeChannelLogo(this, channelId, /*Uri*/ logoUri)
ChannelLogoUtils.storeChannelLogo(this, channelId, /*Bitmap*/ logoBitmap)

// optional, make it the default channel
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
TvContractCompat.requestChannelBrowsable(this, channelId)
