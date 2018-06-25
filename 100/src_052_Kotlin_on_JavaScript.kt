import kotlin.browser.document

fun main(args: Array<String>) {
    val message = "Hello JavaScript!"
    document.getElementById("cont")!!.innerHTML = message
}
