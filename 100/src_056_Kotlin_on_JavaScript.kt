class JsObject {
    @JavascriptInterface
    override fun toString(): String {
        return "Hi from injectedObject"
    }
}
wv.addJavascriptInterface(JsObject(), "injectedObject")
