package com.wadejensen.retirement.tax

fun medicare(income: Double, hasPrivateHealthInsurance: Boolean): Double =
    medicareLevy(income) + medicareLevySurcharge(income, hasPrivateHealthInsurance)


/**
 * @param income Income before tax (must be greater than zero)
 * @param hasPrivateHealthInsurance
 * @return Amount of tax owed due to medicare levy
 */
fun medicareLevy(income: Double): Double  =
    0.02 * income

fun medicareLevySurcharge(income: Double, hasPrivateHealthInsurance: Boolean): Double {
    val surchargeRate = when {
        hasPrivateHealthInsurance || income <=  90_000.0 -> 0.0
        income >  90_000.0        && income <= 105_000.0 -> 0.03
        income > 105_000.0        && income <= 140_000.0 -> 0.0325
        else -> 0.035
    }
    return surchargeRate * income
}
