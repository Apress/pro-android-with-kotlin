@Entity(tableName = "empl", primaryKeys = tableOf("first_name","last_name"))
data class Employee( @ColumnInfo(name = "first_name") var firstName:String, @ColumnInfo(name = "last_name") var lastName:String)
