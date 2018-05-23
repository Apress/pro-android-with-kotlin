// to update:
contentResolver.update( TvContractCompat.buildChannelUri(channelId), channel.toContentValues(), null, null)

// to delete:
contentResolver.delete( TvContractCompat.buildChannelUri(channelId), null, null)
