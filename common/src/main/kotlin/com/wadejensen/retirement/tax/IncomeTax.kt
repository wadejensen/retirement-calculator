package com.wadejensen.retirement.tax

import kotlin.math.max
import kotlin.math.min
import kotlin.math.round

object IncomeTax {
    fun taxableIncome(salary: Double, deductions: Double, capitalGains: Double): Double
    {
        return salary - deductions + capitalGains
    }

    /**
     * @param income Taxable income
     * @return Amount of income tax owed
     */
    fun incomeTax(income: Double): Double
    {
        val incomeTax = when {
            income <= 18_200.0 -> 0.0
            income > 18_200.0 && income <= 37_000.0 -> 0.19 * (income - 18_200.0)
            income > 37_000.0 && income <= 90_000.0 -> 3_572.0 + 0.325 * (income - 37_000.0)
            income > 90_001.0 && income <= 180_000.0 -> 20_797.0 + 0.37 * (income - 90_000.0)
            else -> 54_097.0 + 0.45 * (income - 180_000)
        }
        return round(incomeTax)
    }

    fun taxOffsets(income: Double, incomeTax: Double): Double
    {
        val lowIncomeOffset = lowIncomeTaxOffset(income)
        val lowAndMiddleIncomeOffset = lowAndMiddleIncomeTaxOffset(income)

        // round to nearest cent
        val possibleTaxOffsets = round((lowAndMiddleIncomeOffset + lowIncomeOffset) * 100.0) / 100.0

        // avoid a tax offset causing a tax refund
        return min(incomeTax, possibleTaxOffsets)
    }

    private fun lowIncomeTaxOffset(income: Double): Double
    {
        return if (income <= 37_000) {
            445.0
        }
        else {
            val taxOffsetReduction = 0.015 * (income - 37_000)
            max(0.0, 445.0 - taxOffsetReduction)
        }
    }

    private fun lowAndMiddleIncomeTaxOffset(income: Double): Double
    {
        return if (income <= 37_000) {
            200.0
        }
        else if (income > 37_000 && income <= 48_000) {
            200 + 0.03 * (income - 37_000.0)
        }
        else if (income > 48_000.0 && income <= 90_000.0) {
            530.0
        }
        else {
            val offsetReduction = 0.015 * (income - 90_000.0)
            max(0.0, 530.0 - offsetReduction)
        }
    }
}


