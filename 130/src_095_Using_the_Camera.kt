if((requestCode == REQUEST_VIDEO_CAPTURE and 0xFFFF) && resultCode == Activity.RESULT_OK) {
    videoView.setVideoPath(videoFile!!.absolutePath)
    videoView.start()
}
