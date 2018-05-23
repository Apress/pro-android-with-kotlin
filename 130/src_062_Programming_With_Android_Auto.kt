fun isCarUiMode(c:Context):Boolean {
    val uiModeManager = c.getSystemService(Context.UI_MODE_SERVICE) as
    UiModeManager
    return uiModeManager.getCurrentModeType() == Configuration.UI_MODE_TYPE_CAR
}
