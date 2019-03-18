package com.wadejensen.retirement

interface InvestmentStrategy {
    fun beforeTaxSuperContribution(
        compulsoryContribution: Double,
        taxableIncome: Double,
        annualExpenses: Double
    ): Double

    fun afterTaxSuperContribution(
        compulsoryContribution: Double,
        taxableIncome: Double,
        annualExpenses: Double
    ): Double

    fun investmentContribution(
        compulsoryContribution: Double,
        taxableIncome: Double,
        annualExpenses: Double
    ): Double
}
