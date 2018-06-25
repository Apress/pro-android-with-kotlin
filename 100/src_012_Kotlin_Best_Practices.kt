fun getMaxFactorial():Int = 13
fun fact(n:Int):Int {
    val maxFactorial = getMaxFactorial()
    if(n > maxFactorial)
    throw RuntimeException("Too big")
    var x = 1
    for( i in 1..(n) ) {
        x *= i
    }
    return x
}
val x = fact(12)
System.out.println("12! = ${x}")
