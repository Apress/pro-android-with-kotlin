class MainActivity : AppCompatActivity() {
    companion object {
        val REQUEST_ENABLE_BT = 42
        val REQUEST_QUERY_DEVICES = 142
    }
    var mBluetoothAdapter: BluetoothAdapter? = null
    var mCommandService:BluetoothCommandService? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        val permission1 = ContextCompat.checkSelfPermission( this, Manifest.permission.BLUETOOTH)
        val permission2 = ContextCompat.checkSelfPermission( this, Manifest.permission.BLUETOOTH_ADMIN)
        if (permission1 != PackageManager.PERMISSION_GRANTED || permission2 != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, arrayOf( Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN), 642)
        }
        
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not supported", Toast.LENGTH_LONG).show()
            finish()
        }
        
        if (!mBluetoothAdapter!!.isEnabled()) {
            val enableIntent = Intent( BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult( enableIntent, REQUEST_ENABLE_BT)
        }
    }
    
    
    /**
    * Launch the DeviceListActivity to see devices and
    * do scan
    */
    fun scanDevices(v:View) {
        val serverIntent = Intent( this, DeviceListActivity::class.java)
        startActivityForResult(serverIntent, REQUEST_QUERY_DEVICES)
    }
    
    fun rfComm(v: View) {
        sendMessage("The message")
    }
    
    /**
    * Sends a message.
    *
    * @param message A string of text to send.
    */
    private fun sendMessage(message: String) {
        if (mCommandService?.mState !== BluetoothCommandService.Companion.State.CONNECTED)
        {
            Toast.makeText(this, "Not connected", Toast.LENGTH_SHORT).show()
            return
        }
        
        // Check that there's actually something to send
        if (message.length > 0) {
            val send = message.toByteArray()
            mCommandService?.write(send)
        }
    }
    
    private
    fun connectDevice(data: Intent, secure: Boolean) {
        val macAddress = data.extras!!
        .getString( DeviceListActivity.EXTRA_DEVICE_ADDRESS)
        mBluetoothAdapter?.getRemoteDevice(macAddress)?.run {
            val device = this
            mCommandService = BluetoothCommandService( this@MainActivity, macAddress).apply {
                addStateChangeListener { statex ->
                    runOnUiThread {
                        state.text = statex.toString()
                    }
                }
                connect(device)
            }
        }
    }
    
    private fun fetchUuids(device: BluetoothDevice) {
        device.fetchUuidsWithSdp()
    }
    
    override
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            REQUEST_QUERY_DEVICES -> {
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, false)
                }
            }
        }
    }
}
