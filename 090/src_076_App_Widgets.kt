class ExampleAppWidgetProvider : AppWidgetProvider() {
    override
    fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds:IntArray) {
        // Perform this loop procedure for each App
        // Widget that belongs to this provider
        for(appWidgetId in appWidgetIds) {
            // This is just an example, you can do other
            // stuff here...
            // Create an Intent to launch MainActivity
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
            
            // Attach listener to the button
            val views = RemoteViews(context.getPackageName(), R.layout.appwidget_provider_layout)
            views.setOnClickPendingIntent( R.id.button, pendingIntent)
            
            // Tell the AppWidgetManager to perform an
            // update on the app widget
            appWidgetManager.updateAppWidget( appWidgetId, views)
        }
    }
}
