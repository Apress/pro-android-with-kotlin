fun someFun() {
    ...data class Person( val fName:String, val lName:String, val age:Int)
    fun innerFun():Person = ......val p1:Person = innerFun()
    val fName1 = p1.fName
    ...