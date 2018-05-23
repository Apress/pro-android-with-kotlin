@Entity
data class Employee( @PrimaryKey(autoGenerate = true) var uid:Int = 0, @ColumnInfo(name = "first_name") var firstName:String, @ColumnInfo(name = "last_name") var lastName:String)
