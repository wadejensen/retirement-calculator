package com.wadejensen.retirement.tax

object Medicare {
    fun tax(income: Double, hasPrivateHealthInsurance: Boolean): Double =
        levy(income) + levySurcharge(income, hasPrivateHealthInsurance)

    /**
     * @param income Income before tax (must be greater than zero)
     * @param hasPrivateHealthInsurance
     * @return Amount of tax owed due to medicare levy
     */
    private fun levy(income: Double): Double  =
        0.02 * income

    private fun levySurcharge(income: Double, hasPrivateHealthInsurance: Boolean): Double
    {
        val surchargeRate = when {
            hasPrivateHealthInsurance || income <=  90_000.0 -> 0.0
            income >  90_000.0        && income <= 105_000.0 -> 0.01
            income > 105_000.0        && income <= 140_000.0 -> 0.0125
            else -> 0.035
        }
        return surchargeRate * income
    }
}



