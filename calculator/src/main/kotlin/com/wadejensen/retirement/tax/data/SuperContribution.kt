package com.wadejensen.retirement.tax.data

data class SuperContribution(
    val compulsory: Double,
    val pretax: Double,
    val afterTax: Double
) {
  fun total(): Double = compulsory + pretax + afterTax
}
