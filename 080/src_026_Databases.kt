fun fetchDb() = Room.databaseBuilder( this, MyDatabase::class.java, "MyDatabase.db")
.allowMainThreadQueries()
.build()
val db = fetchDb()
