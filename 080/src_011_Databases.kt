data class Name(var firstName:String, var lastName:String)

@Entity
data class Employee( @PrimaryKey(autoGenerate = true) var uid:Int = 0, @Embedded var name:Name)
