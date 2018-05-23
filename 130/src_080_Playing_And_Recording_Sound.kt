val mRecorder = MediaRecorder().apply {
    setAudioSource(MediaRecorder.AudioSource.MIC)
    setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
    setOutputFile(mFileName)
    setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
}
mRecorder.prepare()
mRecorder.start()

... later: mRecorder.stop()
