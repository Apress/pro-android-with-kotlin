val wnbuilder = WatchNextProgram.Builder()
val watchNextType = TvContractCompat.WatchNextPrograms.WATCH_NEXT_TYPE_CONTINUE
wnbuilder.setType( TvContractCompat.WatchNextPrograms.TYPE_CLIP)
.setWatchNextType(watchNextType)
.setLastEngagementTimeUtcMillis(time)
.setTitle("Title")
.setDescription("Program description")
.setPosterArtUri(largePosterArtUri)
.setIntentUri(Uri.parse(progLink))
.setInternalProviderId(appProgramId)
val watchNextProgram = wnbuilder.build()
val watchNextProgramUri = contentResolver
.insert( TvContractCompat.WatchNextPrograms.CONTENT_URI, watchNextProgram.toContentValues())
val watchnextProgramId = ContentUris.parseId(watchNextProgramUri)
