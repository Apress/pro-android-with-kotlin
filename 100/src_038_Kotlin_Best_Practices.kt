class A {
    val b = 7
    init {
        val p = arrayOf(8,9).apply {
            this[0] += this@A.b
        }
        ...}
}
