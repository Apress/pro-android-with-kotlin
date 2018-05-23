val ld: LiveData<List<Employee>> = employeeDao.getAllSync()
ld.observeForever { l ->
    l?.forEach { empl ->
        Log.e("LOG", empl.toString())
        // do s.th. else with the employee
    }
}
