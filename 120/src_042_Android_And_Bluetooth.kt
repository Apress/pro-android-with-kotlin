/**
* This Activity appears as a dialog. It lists any
* paired devices and devices detected in the area after
* discovery. When a device is chosen by the user, the
* MAC address of the device is sent back to the parent
* Activity in the result Intent.
*/
class DeviceListActivity : Activity() {
    companion object {
        private val TAG = "DeviceListActivity"
        var EXTRA_DEVICE_ADDRESS = "device_address"
    }
    
    private var mBtAdapter: BluetoothAdapter? = null
    private var mNewDevicesArrayAdapter: ArrayAdapter<String>? = null
    
    private val mDeviceClickListener = AdapterView.OnItemClickListener {
        av, v, arg2, arg3 ->
        // Cancel discovery because it's costly and we're
        // about to connect
        mBtAdapter!!.cancelDiscovery()
        
        // Get the device MAC address, which is the last
        // 17 chars in the View
        val info = (v as TextView).text.toString()
        val address = info.substring(info.length - 17)
        
        // Create the result Intent and include the MAC
        // address
        val intent = Intent()
        intent.putExtra(EXTRA_DEVICE_ADDRESS, address)
        
        // Set result and finish this Activity
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
    
    /**
    * The BroadcastReceiver that listens for discovered
    * devices and changes the title when discovery is
    * finished
    */
    private val mReceiver = object : BroadcastReceiver() {
        override
        fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND == action) {
                // Get the BluetoothDevice object from
                // the Intent
                val device = intent.getParcelableExtra<BluetoothDevice>( BluetoothDevice.EXTRA_DEVICE)
                // If it's already paired, skip it,
                // because it's been listed already
                if (device.bondState != BluetoothDevice.BOND_BONDED) {
                    mNewDevicesArrayAdapter!!.add( device.name + "\n" + device.address)
                }
                // When discovery is finished, change the
                // Activity title
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED == action) {
                setProgressBarIndeterminateVisibility( false)
                setTitle("Select Device")
                if (mNewDevicesArrayAdapter!!.count == 0) {
                    val noDevices = "No device"
                    mNewDevicesArrayAdapter!!.add( noDevices)
                }
            }
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Setup the window
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS)
        setContentView(R.layout.activity_device_list)
        
        // Set result CANCELED in case the user backs out
        setResult(Activity.RESULT_CANCELED)
        
        // Initialize the button to perform device
        // discovery
        button_scan.setOnClickListener { v ->
            doDiscovery()
            v.visibility = View.GONE
        }
        
        // Initialize array adapters. One for already
        // paired devices and one for newly discovered
        // devices
        val pairedDevicesArrayAdapter = ArrayAdapter<String>(this, R.layout.device_name)
        mNewDevicesArrayAdapter = ArrayAdapter(this, R.layout.device_name)
        
        // Find and set up the ListView for paired devices
        val pairedListView = paired_devices as ListView
        pairedListView.adapter = pairedDevicesArrayAdapter
        pairedListView.onItemClickListener = mDeviceClickListener
        
        // Find and set up the ListView for newly
        // discovered devices
        val newDevicesListView = new_devices as ListView
        newDevicesListView.adapter = mNewDevicesArrayAdapter
        newDevicesListView.onItemClickListener = mDeviceClickListener
        
        // Register for broadcasts when a device is
        // discovered
        var filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        this.registerReceiver(mReceiver, filter)
        
        // Register for broadcasts when discovery has
        // finished
        filter = IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        this.registerReceiver(mReceiver, filter)
        
        // Get the local Bluetooth adapter
        mBtAdapter = BluetoothAdapter.getDefaultAdapter()
        
        // Get a set of currently paired devices
        val pairedDevices = mBtAdapter!!.bondedDevices
        
        // If there are paired devices, add each one to
        // the ArrayAdapter
        if (pairedDevices.size > 0) {
            title_paired_devices.visibility = View.VISIBLE
            for (device in pairedDevices) {
                pairedDevicesArrayAdapter.add( device.name + "\n" + device.address)
            }
        } else {
            val noDevices = "No devices"
            pairedDevicesArrayAdapter.add(noDevices)
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        
        // Make sure we're not doing discovery anymore
        if (mBtAdapter != null) {
            mBtAdapter!!.cancelDiscovery()
        }
        
        // Unregister broadcast listeners
        this.unregisterReceiver(mReceiver)
    }
    
    /**
    * Start device discover with the BluetoothAdapter
    */
    private fun doDiscovery() {
        Log.d(TAG, "doDiscovery()")
        
        // Indicate scanning in the title
        setProgressBarIndeterminateVisibility(true)
        setTitle("Scanning")
        
        // Turn on sub-title for new devices
        title_new_devices.visibility = View.VISIBLE
        
        // If we're already discovering, stop it
        if (mBtAdapter!!.isDiscovering) {
            mBtAdapter!!.cancelDiscovery()
        }
        
        // Request discover from BluetoothAdapter
        mBtAdapter!!.startDiscovery()
    }
}
