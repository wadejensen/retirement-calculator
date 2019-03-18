package com.wadejensen.retirement.tax.data

data class SuperTax(
    val concessional: Double,
    val nonConcessional: Double,
    val division293: Double
) {
    fun total(): Double = concessional + nonConcessional + division293
}
