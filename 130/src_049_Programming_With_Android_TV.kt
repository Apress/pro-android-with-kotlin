// to update:
contentResolver.update( TvContractCompat.buildPreviewProgramUri(programId), watchNextProgram.toContentValues(), null, null)

// to delete:
contentResolver.delete( TvContractCompat.buildPreviewProgramUri(programId), null, null)
