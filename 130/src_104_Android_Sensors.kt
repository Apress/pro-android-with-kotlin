val sensorManager = getSystemService( Context.SENSOR_SERVICE) as SensorManager
val deviceSensors = sensorManager.getSensorList(Sensor.TYPE_ALL)
deviceSensors.forEach { sensor ->
    Log.e("LOG", "+++" + sensor.toString())
}
