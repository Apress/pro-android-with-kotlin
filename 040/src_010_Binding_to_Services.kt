internal class InHandler(val ctx: Context) : Handler() {
    override
    fun handleMessage(msg: Message) {
        val s = msg.data.getString("MyString")
        Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show()
    }
}
[...]
class MyService : Service() {
    val myMessg:Messenger = Messenger(InHandler(this))
    [...]
}
