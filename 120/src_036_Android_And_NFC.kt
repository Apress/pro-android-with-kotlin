/**
* Utility class for persisting account numbers to disk.
*
* The default SharedPreferences instance is used as
* the backing storage. Values are cached in memory for
* performance.
*/
object AccountStorage {
    private val PREF_ACCOUNT_NUMBER = "account_number"
    private val DEFAULT_ACCOUNT_NUMBER = "00000000"
    private val TAG = "AccountStorage"
    private var sAccount: String? = null
    private val sAccountLock = Any()
    
    fun setAccount(c: Context, s: String) {
        synchronized(sAccountLock) {
            Log.i(TAG, "Setting account number: $s")
            val prefs = PreferenceManager.getDefaultSharedPreferences(c)
            prefs.edit().putString(PREF_ACCOUNT_NUMBER, s).commit()
            sAccount = s
        }
    }
    
    fun getAccount(c: Context): String? {
        synchronized(sAccountLock) {
            if (sAccount == null) {
                val prefs = PreferenceManager.getDefaultSharedPreferences(c)
                val account = prefs.getString( PREF_ACCOUNT_NUMBER, DEFAULT_ACCOUNT_NUMBER)
                sAccount = account
            }
            return sAccount
        }
    }
}
