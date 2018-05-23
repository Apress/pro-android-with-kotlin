val compName = ComponentName(applicationContext, MyService::class.java)

val providerUpdateRequester = ProviderUpdateRequester( applicationContext, componentName)

providerUpdateRequester.requestUpdate( complicationId)
// To instead all complications, instead use
// providerUpdateRequester.requestUpdateAll()
