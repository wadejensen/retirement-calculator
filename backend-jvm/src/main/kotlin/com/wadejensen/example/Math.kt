package com.wadejensen.example

import com.wadejensen.example.IMath

actual class Math : IMath {
    actual override fun sqrt(x: Double): Double {
        return kotlin.math.sqrt(x)
    }
}
