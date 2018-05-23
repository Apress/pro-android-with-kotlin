class Derived(val b: Printer) : Printer by b {
    override fun print() {
        print("Printing:")
        b.print()
    }
}
