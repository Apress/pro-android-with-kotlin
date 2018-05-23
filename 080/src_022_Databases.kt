val ld: LiveData<List<Employee>> = employeeDao.getAllSync()
val lcOwn : LifecycleOwner = ...ld.observe(lcOwn, { l ->
    l?.forEach { empl ->
        Log.e("LOG", empl.toString())
        // do s.th. else with the employee
    }
} )
