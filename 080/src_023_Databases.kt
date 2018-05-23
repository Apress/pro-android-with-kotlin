@Query("SELECT * FROM employee" + " WHERE uid = :uid")
fun findByIdRx(uid: Int): Flowable<Employee>
