val havePermissions = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR)
== PackageManager.PERMISSION_GRANTED
&& ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR)
== PackageManager.PERMISSION_GRANTED
if(!havePermissions) {
    // Acquire permissions...
}else{
    data class CalEntry(val name: String, val id: String)
    val calendars = HashMap<String, CalEntry>()
    val uri = CalendarContract.Calendars.CONTENT_URI
    val cursor = contentResolver.query( uri, null, null, null, null)
    cursor.moveToFirst()
    while (!cursor.isAfterLast) {
        val calName = cursor.getString( cursor.getColumnIndex( CalendarContract.Calendars.NAME))
        val calId = cursor.getString( cursor.getColumnIndex( CalendarContract.Calendars._ID))
        calendars[calName] = CalEntry(calName, calId)
        cursor.moveToNext()
    }
    Log.e("LOG", calendars.toString())
    
    val calId = "4" // You should instead fetch an
    // appropriate entry from the map!
    val year = 2018
    val month = Calendar.AUGUST
    val dayInt = 27
    val hour = 8
    val minute = 30
    
    val beginTime = Calendar.getInstance()
    beginTime.set(year, month, dayInt, hour, minute)
    val event = ContentValues()
    event.put(CalendarContract.Events.CALENDAR_ID, calId)
    event.put(CalendarContract.Events.TITLE, "MyEvent")
    event.put(CalendarContract.Events.DESCRIPTION, "This is test event")
    event.put(CalendarContract.Events.EVENT_LOCATION, "School")
    event.put(CalendarContract.Events.DTSTART, beginTime.getTimeInMillis())
    event.put(CalendarContract.Events.DTEND, beginTime.getTimeInMillis())
    event.put(CalendarContract.Events.ALL_DAY, 0)
    event.put(CalendarContract.Events.RRULE, "FREQ=YEARLY")
    event.put(CalendarContract.Events.EVENT_TIMEZONE, "Germany")
    val retUri = contentResolver.insert( CalendarContract.Events.CONTENT_URI, event)
    Log.e("LOG", retUri.toString())
}
