@Entity(indices = arrayOf( Index(value = arrayOf("ssn"), unique = true) ) )
data class Employee( @PrimaryKey(autoGenerate = true) var uid:Int = 0, var ssn:String, @Embedded var name:Name)
