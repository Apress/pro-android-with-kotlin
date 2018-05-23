@RunWith(TestAll.TestAllRunner::class)
class TestAll {
    class TestAllRunner(klass: Class<*>?, runners0: List<Runner>) : ParentRunner<Runner>(klass) {
        private val runners: List<Runner>
        
        constructor(clazz: Class<*>) : t
        his(clazz, listOf<Runner>()) {
        }
        
        init {
            val classLoadersList = arrayOf( ClasspathHelper.contextClassLoader(), ClasspathHelper.staticClassLoader())
            
            val reflections = Reflections( ConfigurationBuilder() .setScanners(SubTypesScanner(false), TypeAnnotationsScanner()) .setUrls(ClasspathHelper.forClassLoader( *classLoadersList)) .filterInputsBy(FilterBuilder().include(FilterBuilder.prefix( javaClass.`package`.name))))
            
            runners = reflections.getTypesAnnotatedWith( RunWith::class.java).filter {
                clazz ->
                clazz.getAnnotation(RunWith::class.java).value.toString().contains(".PowerMockRunner")
            }.map { PowerMockRunner(it) }
        }
        
        override fun getChildren(): List<Runner> = runners
        
        override fun describeChild(child: Runner): Description = child.description
        
        override fun runChild(runner: Runner, notifier: RunNotifier) {
            runner.run(notifier)
        }
    }
}
