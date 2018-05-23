package ...
import android.telephony.TelephonyManager as TM
import ...
class CallMonitor : BroadcastReceiver() {
    companion object {
        private var lastState = TM.CALL_STATE_IDLE
        private var callStartTime: Date? = null
        private var isIncoming: Boolean = false
        private var savedNumber: String? = null
    }
    
    override
    fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_NEW_OUTGOING_CALL) {
            savedNumber = intent.extras!!.getString(Intent.EXTRA_PHONE_NUMBER)
        } else {
            val stateStr = intent.extras!!.getString(TM.EXTRA_STATE)
            val number = intent.extras!!.getString(TM.EXTRA_INCOMING_NUMBER)
            val state = when(stateStr) {
                TM.EXTRA_STATE_IDLE ->
                TM.CALL_STATE_IDLE
                TM.EXTRA_STATE_OFFHOOK ->
                TM.CALL_STATE_OFFHOOK
                TM.EXTRA_STATE_RINGING ->
                TM.CALL_STATE_RINGING
                else -> 0
            }
            callStateChanged(context, state, number)
        }
    }
    
    protected fun onIncomingCallReceived( ctx: Context, number: String?, start: Date){
        Log.e("LOG", "IncomingCallReceived ${number} ${start}")
    }
    
    protected fun onIncomingCallAnswered( ctx: Context, number: String?, start: Date) {
        Log.e("LOG", "IncomingCallAnswered ${number} ${start}")
    }
    
    protected fun onIncomingCallEnded( ctx: Context, number: String?, start: Date?, end: Date) {
        Log.e("LOG", "IncomingCallEnded ${number} ${start}")
    }
    
    protected fun onOutgoingCallStarted( ctx: Context, number: String?, start: Date) {
        Log.e("LOG", "OutgoingCallStarted ${number} ${start}")
    }
    
    protected fun onOutgoingCallEnded( ctx: Context, number: String?, start: Date?, end: Date) {
        Log.e("LOG", "OutgoingCallEnded ${number} ${start}")
    }
    
    protected fun onMissedCall( ctx: Context, number: String?, start: Date?) {
        Log.e("LOG", "MissedCall ${number} ${start}")
    }
    
    /**
    * Incoming call:
    *     IDLE -> RINGING when it rings,
    *     -> OFFHOOK when it's answered,
    *     -> IDLE when its hung up
    * Outgoing call:
    *     IDLE -> OFFHOOK when it dials out,
    *     -> IDLE when hung up
    */
    private fun callStateChanged( context: Context, state: Int, number: String?) {
        if (lastState == state) {
            return // no change in state
        }
        when (state) {
            TM.CALL_STATE_RINGING -> {
                isIncoming = true
                callStartTime = Date()
                savedNumber = number
                onIncomingCallReceived( context, number, callStartTime!!)
            }
            TM.CALL_STATE_OFFHOOK ->
            if (lastState != TM.CALL_STATE_RINGING) {
                isIncoming = false
                callStartTime = Date()
                onOutgoingCallStarted(context, savedNumber, callStartTime!!)
            } else {
                isIncoming = true
                callStartTime = Date()
                onIncomingCallAnswered(context, savedNumber, callStartTime!!)
            }
            TM.CALL_STATE_IDLE ->
            if (lastState == TM.CALL_STATE_RINGING) {
                //Ring but no pickup-  a miss
                onMissedCall(context, savedNumber, callStartTime)
            } else if (isIncoming) {
                onIncomingCallEnded(context, savedNumber, callStartTime, Date())
            } else {
                onOutgoingCallEnded(context, savedNumber, callStartTime, Date())
            }
        }
        lastState = state
    }
}
