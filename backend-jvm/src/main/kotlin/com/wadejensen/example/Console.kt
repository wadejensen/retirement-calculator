package com.wadejensen.example

import com.wadejensen.example.IConsole

actual class Console : IConsole {
    actual override fun println(s: String): Unit = println(s)
}
