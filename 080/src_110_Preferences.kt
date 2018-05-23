class MySettingsFragment : PreferenceFragment(), SharedPreferences.OnSharedPreferenceChangeListener {
    companion object {
        val DELETE_LIMIT = "pref_key_delete_limit"
        val LIST = "pref_key_list"
        val RINGTONE = "pref_key_ringtone"
    }
    
    override fun onSharedPreferenceChanged( sharedPreferences: SharedPreferences?, key: String?) {
        sharedPreferences?.run {
            when(key) {
                DELETE_LIMIT -> {
                    findPreference(key).summary = getString(key, "") ?: "10"
                }
                LIST -> {
                    findPreference(key).summary = (findPreference(key) as ListPreference).entry
                }
                RINGTONE -> {
                    val uriStr = getString(key, "") ?: ""
                    findPreference(key).summary = getRingtoneName(Uri.parse(uriStr))
                }
            }
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences)
        
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(activity)
        sharedPref.registerOnSharedPreferenceChangeListener( this)
        
        with(sharedPref) {
            findPreference(DELETE_LIMIT).summary = getString(DELETE_LIMIT, "10")
            findPreference(LIST).summary = (findPreference(LIST) as ListPreference).let {
                val ind = Math.max(0, it.findIndexOfValue(it.value))
                resources.getStringArray(listentries)[ind]
            }
            findPreference(RINGTONE).summary = getRingtoneName( Uri.parse(getString(RINGTONE, "") ?: ""))
        }
    }
    
    fun getRingtoneName(uri:Uri):String {
        return activity.contentResolver.query(uri, null, null, null, null)?.let {
            it.moveToFirst()
            val res = it.getString( it.getColumnIndex( MediaStore.MediaColumns.TITLE))
            it.close()
            res
        } ?: ""
    }
}
