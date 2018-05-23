class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var sensorManager:SensorManager
    private lateinit var magneticFieldSensor:Sensor
    
    override fun onCreate(savedInstanceState: Bundle?) {
        ...sensorManager = getSystemService(Context.SENSOR_SERVICE)
        as SensorManager
        magneticFieldSensor = sensorManager.getDefaultSensor( Sensor.TYPE_MAGNETIC_FIELD)
    }
    
    override
    fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // Do something here if sensor accuracy changes.
    }
    
    override
    fun onSensorChanged(event: SensorEvent) {
        Log.e("LOG", Arrays.toString(event.values))
        // Do something with this sensor value.
    }
    
    override
    fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, magneticFieldSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }
    
    override
    fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
}
