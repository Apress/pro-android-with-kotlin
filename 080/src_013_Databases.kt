data class Name( @ColumnInfo(name = "first_name") var firstName:String, @ColumnInfo(name = "last_name") var lastName:String)

@Entity
data class Employee( @PrimaryKey(autoGenerate = true) var uid:Int = 0, @Embedded var name:Name)
