fun fetchDb() = Room.inMemoryDatabaseBuilder( this, MyDatabase::class.java)
.build()
val db = fetchDb()
