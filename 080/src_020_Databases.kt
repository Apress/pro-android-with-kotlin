@Query("SELECT * FROM employee")
fun getAllSync(): LiveData<List<Employee>>
