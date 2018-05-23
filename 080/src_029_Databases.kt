val migs = arrayOf( object : Migration(1,2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // code for the 1->2 migration...
        // this is already running inside a transaction,
        // don't add your own transaction code here!
    }
}, object : Migration(2,3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // code for the 2->3 migration...
        // this is already running inside a transaction,
        // don't add your own transaction code here!
    }
} // more migrations ...
)

private fun fetchDb() = Room.databaseBuilder(
this, MyDatabase::class.java, "MyDatabase.db")
.addMigrations(*migs)
.build()
