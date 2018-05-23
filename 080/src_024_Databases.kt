fun fetchDb() = Room.databaseBuilder( this, MyDatabase::class.java, "MyDatabase.db")
.build()
val db = fetchDb()
