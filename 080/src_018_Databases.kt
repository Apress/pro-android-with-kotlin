@Dao
interface EmployeeDao {
    @Query("SELECT * FROM employee")
    fun getAll(): List<Employee>
    
    @Query("SELECT * FROM employee" + " WHERE uid IN (:uIds)")
    fun loadAllByIds(uIds: IntArray): List<Employee>
    
    @Query("SELECT * FROM employee" + " WHERE last_name LIKE :name")
    fun findByLastName(name: String): List<Employee>
    
    @Query("SELECT * FROM employee" + " WHERE last_name LIKE :lname AND " + "       first_name LIKE :fname LIMIT 1")
    fun findByName(lname: String, fname: String): Employee
    
    @Query("SELECT * FROM employee" + " WHERE uid = :uid")
    fun findById(uid: Int): Employee
    
    @Insert
    fun insert(vararg employees: Employee): LongArray
    
    @Update
    fun update(vararg employees: Employee)
    
    @Delete
    fun delete(vararg employees: Employee)
}
