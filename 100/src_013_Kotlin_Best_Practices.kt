fun fact(n:Int):Int = if(n>getMaxFactorial())
throw RuntimeException("Too big") else
if(n > 1) n * fact(n-1) else 1
val x = fact(10)
System.out.println("10! = ${x}")
