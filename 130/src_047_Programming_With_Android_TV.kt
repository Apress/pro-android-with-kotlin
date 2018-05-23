val pbuilder = PreviewProgram.Builder()

// Intent to launch when a program gets selected
val progLink = Intent().toUri(Intent.URI_INTENT_SCHEME)

pbuilder.setChannelId(channelId)
.setType(TvContractCompat.PreviewPrograms.TYPE_CLIP)
.setTitle("Title")
.setDescription("Program description")
.setPosterArtUri(largePosterArtUri)
.setIntentUri(Uri.parse(progLink))
.setInternalProviderId(appProgramId)
val previewProgram = pbuilder.build()
val programUri = contentResolver.insert( TvContractCompat.PreviewPrograms.CONTENT_URI, previewProgram.toContentValues())
val programId = ContentUris.parseId(programUri)
