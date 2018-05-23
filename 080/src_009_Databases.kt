@Entity( foreignKeys = arrayOf( ForeignKey(entity = Employee::class, parentColumns = arrayOf( "uid" ), childColumns = arrayOf( "employeeId" ), onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE, deferred = true)), indices = arrayOf( Index("employeeId")) )
@Entity
data class Contact( @PrimaryKey(autoGenerate = true) var uid:Int = 0, var employeeId:Int, var emailAddr:String)
