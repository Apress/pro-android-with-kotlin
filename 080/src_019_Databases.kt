@Dao
interface ContactDao {
    @Insert
    fun insert(vararg contacts: Contact)
    
    @Query("SELECT * FROM Contact WHERE uid = :uId")
    fun findById(uId: Int): List<Contact>
    
    @Query("SELECT * FROM Contact WHERE" + " employeeId = :employeeId")
    fun loadByEmployeeId(employeeId: Int): List<Contact>
}

data class Person(@Embedded var name:Name?, var emailAddr: String?)
@Dao
interface PersonDao {
    @Query("SELECT * FROM empl" + " LEFT OUTER JOIN Contact ON" + "     empl.uid = Contact.employeeId" + " WHERE empl.uid = :uId")
    fun findById(uId: Int): List<Person>
}
