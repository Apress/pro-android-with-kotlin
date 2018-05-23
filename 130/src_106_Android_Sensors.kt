val sensorManager = getSystemService( Context.SENSOR_SERVICE) as SensorManager
val magneticFieldSensor = sensorManager.getDefaultSensor( Sensor.TYPE_MAGNETIC_FIELD)
sensorManager.registerListener(this, magneticFieldSensor, SensorManager.SENSOR_DELAY_NORMAL)
