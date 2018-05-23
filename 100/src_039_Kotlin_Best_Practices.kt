interface Printer {
    fun print()
}

class PrinterImpl(val x: Int) : Printer {
    override fun print() { print(x) }
}

class Derived(b: Printer) : Printer by b
