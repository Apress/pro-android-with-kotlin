class A {
    fun go(l:MutableList<String>) {
        Thread {
            while (true) {
                l.add("" + System.currentTimeMillis())
                Thread.sleep(1)
            }
        }.start()
    }
}
