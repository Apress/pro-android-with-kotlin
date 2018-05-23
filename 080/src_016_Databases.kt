@Database(entities = arrayOf(Employee::class, Contact::class), version = 1)
abstract class MyDatabase : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao
    abstract fun contactDao(): ContactDao
    abstract fun personDao(): PersonDao
}
