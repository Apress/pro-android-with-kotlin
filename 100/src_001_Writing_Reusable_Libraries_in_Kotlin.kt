package com.example.regularexpressionlib

infix operator fun String.div(re:String) : Array<MatchResult> = Regex(re).findAll(this).toList().toTypedArray()

infix operator fun String.rem(re:String) : MatchResult? = Regex(re).findAll(this).toList().firstOrNull()

operator fun MatchResult.get(i:Int) = this.groupValues[i]

fun String.onMatch(re:String, func: (String)-> Unit)
: Boolean = this.matches(Regex(re)).also { if(it) func(this) }
