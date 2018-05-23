with(progressBar) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
    min = 0
    max = 100
}
var value = 0
Thread {
    while(value < 100) {
        value += 1
        Thread.sleep(200)
        runOnUiThread {
            progressBar.progress = value
        }
    }
    progressBar.visibility = View.INVISIBLE
}.start()
