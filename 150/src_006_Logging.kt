fun v(tag: String, msg: ()->String) {
    if(!ENABLED) return
    Logger.getLogger(tag).trace(msg.invoke())
}
... similar for the other statements
