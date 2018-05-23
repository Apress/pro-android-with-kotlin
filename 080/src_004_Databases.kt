@Entity
data class Employee( @PrimaryKey(autoGenerate = true) var uid:Int = 0, var firstName:String, var lastName:String)

@Entity
data class Contact( @PrimaryKey(autoGenerate = true) var uid:Int = 0, var emailAddr:String)
