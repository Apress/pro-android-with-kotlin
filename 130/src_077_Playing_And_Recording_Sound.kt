mPlayer = mPlayer?.run {
    (A)
    this
} ?: MediaPlayer().apply {
    (B)
}
