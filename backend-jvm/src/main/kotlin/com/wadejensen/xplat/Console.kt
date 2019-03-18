package com.wadejensen.xplat

actual class Console : IConsole {
    actual override fun println(s: String): Unit = println(s)
}
