@Entity(indices = arrayOf( Index("employeeId"), Index(value = arrayOf("country","city")) ) )
data class Contact( @PrimaryKey(autoGenerate = true) var uid:Int = 0, var employeeId:Int, var emailAddr:String, var country:String, var city:String)
