val factEngine:  (acc:Int,i:Int) -> Int = { acc,i -> acc * i }
fun fact(n:Int) = (1..n).fold(1, factEngine)
System.out.println("10! = ${fact(10)}")
