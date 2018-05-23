class BluetoothCommandService(context: Context, val macAddress:String) {
    companion object {
        // Unique UUID for this application
        private val MY_UUID_INSECURE = UUID.fromString( "04c6093b-0000-1000-8000-00805f9b34fb")
        
        // Constants that indicate the current connection
        // state
        enum class State {
            NONE,       // we're doing nothing
            LISTEN,     // listening for incoming conns
            CONNECTING, // initiating an outgoing conn
            CONNECTED   // connected to a remote device
        }
    }
    
    private val mAdapter: BluetoothAdapter
    private var createSocket: CreateSocketThread? = null
    private var readWrite: SocketReadWrite? = null
    var mState: State = State.NONE
    
    private var stateChangeListeners = mutableListOf<(State)->Unit>()
    fun addStateChangeListener(l:(State)->Unit) {
        stateChangeListeners.add(l)
    }
    
    init {
        mAdapter = BluetoothAdapter.getDefaultAdapter()
        changeState(State.NONE)
    }
    
    /**
    * Initiate a connection to a remote device.
    *
    * @param device The BluetoothDevice to connect
    */
    fun connect(device: BluetoothDevice) {
        stopThreads()
        
        // Start the thread to connect with the given
        // device
        createSocket = CreateSocketThread(device).apply {
            start()
        }
    }
    
    /**
    * Stop all threads
    */
    fun stop() {
        stopThreads()
        changeState(State.NONE)
    }
    
    /**
    * Write to the ConnectedThread in an unsynchronized
    * manner
    *
    * @param out The bytes to write
    * @see ConnectedThread.write
    */
    fun write(out: ByteArray) {
        if (mState != State.CONNECTED) return
        readWrite?.run { write(out) }
    }
    
    /////////////////////////////////////////////////////
    /////////////////////////////////////////////////////
    
    /**
    * Start the ConnectedThread to begin managing a
    * Bluetooth connection
    *
    * @param socket The BluetoothSocket on which the
    *       connection was made
    * @param device The BluetoothDevice that has been
    *       connected
    */
    private fun connected(socket: BluetoothSocket, device: BluetoothDevice) {
        stopThreads()
        
        // Start the thread to perform transmissions
        readWrite = SocketReadWrite(socket).apply {
            start()
        }
    }
    
    private fun stopThreads() {
        createSocket?.run {
            cancel()
            createSocket = null
        }
        readWrite?.run {
            cancel()
            readWrite = null
        }
    }
    
    /**
    * Indicate that the connection attempt failed.
    */
    private fun connectionFailed() {
        changeState(State.NONE)
    }
    
    /**
    * Indicate that the connection was lost.
    */
    private fun connectionLost() {
        changeState(State.NONE)
    }
    
    /**
    * This thread runs while attempting to make an
    * outgoing connection with a device. It runs straight
    * through; the connection either succeeds or fails.
    */
    private inner
    class CreateSocketThread( private val mmDevice: BluetoothDevice) : Thread() {
        private val mmSocket: BluetoothSocket?
        
        init {
            // Get a BluetoothSocket for a connection
            // with the given BluetoothDevice
            mmSocket = mmDevice.createInsecureRfcommSocketToServiceRecord( MY_UUID_INSECURE)
            changeState(Companion.State.CONNECTING)
        }
        
        override fun run() {
            name = "CreateSocketThread"
            
            // Always cancel discovery because it will
            // slow down a connection
            mAdapter.cancelDiscovery()
            
            // Make a connection to the BluetoothSocket
            try {
                // This is a blocking call and will only
                // return on a successful connection or an
                // exception
                mmSocket!!.connect()
            } catch (e: IOException) {
                Log.e("LOG","Connection failed", e)
                Log.e("LOG", "Maybe device does not " + " expose service " + MY_UUID_INSECURE)
                // Close the socket
                mmSocket!!.close()
                
                connectionFailed()
                return
            }
            
            // Reset the thread because we're done
            createSocket = null
            
            // Start the connected thread
            connected(mmSocket, mmDevice)
        }
        
        fun cancel() {
            mmSocket!!.close()
        }
    }
    
    /**
    * This thread runs during a connection with a
    * remote device. It handles all incoming and outgoing
    * transmissions.
    */
    private inner
    class SocketReadWrite(val mmSocket: BluetoothSocket) : Thread() {
        private val mmInStream: InputStream?
        private val mmOutStream: OutputStream?
        
        init {
            mmInStream = mmSocket.inputStream
            mmOutStream = mmSocket.outputStream
            changeState(Companion.State.CONNECTED)
        }
        
        override fun run() {
            val buffer = ByteArray(1024)
            var bytex: Int
            
            // Keep listening to the InputStream while
            // connected
            while (mState == Companion.State.CONNECTED) {
                try {
                    // Read from the InputStream
                    bytex = mmInStream!!.read(buffer)
                } catch (e: IOException) {
                    connectionLost()
                    break
                }
            }
        }
        
        /**
        * Write to the connected OutStream.
        *
        * @param buffer The bytes to write
        */
        fun write(buffer: ByteArray) {
            mmOutStream!!.write(buffer)
        }
        
        fun cancel() {
            mmSocket.close()
        }
    }
    
    private fun changeState(newState:State) {
        Log.e("LOG", "changing state: ${mState} -> ${newState}")
        mState = newState
        stateChangeListeners.forEach { it(newState) }
    }
}
