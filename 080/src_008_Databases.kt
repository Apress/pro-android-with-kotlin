@Entity(tableName = "empl")
data class Employee( @PrimaryKey(autoGenerate = true) var uid:Int = 0, var firstName:String = "", var lastName:String = "", @Ignore var salary:Int)
