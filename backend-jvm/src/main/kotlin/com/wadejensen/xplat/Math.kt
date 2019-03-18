package com.wadejensen.xplat

actual class Math : IMath {
    actual override fun sqrt(x: Double): Double {
        return kotlin.math.sqrt(x)
    }
}
