package com.example.app.util

val PI_SQUARED = Math.PI * Math.PI

fun logObj(o:Any?) = o?.let { "(" + o::class.toString() + ") " + o.toString() } ?: "<null>"
