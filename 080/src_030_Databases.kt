//...
object : Migration(1,2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE components "+ "ADD COLUMN salary INTEGER DEFAULT 0;")
    }
}
//...
object : Migration(2,3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE components "+ "ADD COLUMN childCount INTEGER DEFAULT 0;")
    }
}
//...
object : Migration(1,3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE components "+ "ADD COLUMN salary INTEGER DEFAULT 0;")
        db.execSQL("ALTER TABLE components "+ "ADD COLUMN childCount INTEGER DEFAULT 0;")
    }
}
//...
