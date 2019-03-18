package com.wadejensen.retirement.tax.data

data class IncomeTaxPayable(
    val incomeTax: Double,
    val medicare: Double,
    val taxOffset: Double
) {
  fun total(): Double = incomeTax + medicare - taxOffset
}
