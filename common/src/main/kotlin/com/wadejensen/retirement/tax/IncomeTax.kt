package com.wadejensen.retirement.tax

import kotlin.math.max
import kotlin.math.min
import kotlin.math.round

fun taxableIncome(salary: Double, deductions: Double, capitalGains: Double): Double =
    salary - deductions + capitalGains

/**
 * @param income Taxable income
 * @return Amount of income tax owed
 */
fun incomeTax(income: Double): Double {
    val incomeTax = when {
        income <= 18_200.0 -> 0.0
        income > 18_200.0 && income <= 37_000.0 -> 0.19 * (income - 18_200.0)
        income > 37_000.0 && income <= 90_000.0 -> 3_572.0 + 0.325 * (income - 37_000.0)
        income > 90_001.0 && income <= 180_000.0 -> 20_797.0 + 0.37 * (income - 90_000.0)
        else -> 54_097.0 + 0.45 * (income - 180_000)
    }

    return round(incomeTax)
}

fun incomeTaxOffsets(income: Double, incomeTax: Double): Double {
    val lowIncomeOffset = lowIncomeTaxOffset(income)
    val lowAndMiddleIncomeOffset = lowAndMiddleIncomeTaxOffset(income)

    // round to nearest cent
    val possibleTaxOffsets = round((lowAndMiddleIncomeOffset + lowIncomeOffset) * 100.0) / 100.0

    // avoid a tax offset causing a tax refund
    return min(incomeTax, possibleTaxOffsets)
}

internal fun lowIncomeTaxOffset(income: Double): Double =
    if (income <= 37_000) {
        445.0
    }
    else {
        val taxOffsetReduction = 0.015 * (income - 37_000)
        max(0.0, 445.0 - taxOffsetReduction)
    }


internal fun lowAndMiddleIncomeTaxOffset(income: Double): Double =
    if (income <= 37_000) {
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



//fun capitalGainsTax()

// we want to work out take home pay and super that ends up in my account


/**
 * @param salary Cash salary package non-inclusive of super
 * @param financialYear The financial year for tax purposes
 * @return Compulsory employer superannuation contribution amount
 */
fun compulsorySuperContribution(salary: Double, financialYear: Int): Double =
    salary * compsulsorySuperRate(financialYear)


fun compsulsorySuperRate(financialYear: Int): Double =
    when {
        financialYear <= 2013 -> 0.09
        financialYear == 2014 -> 0.0925
        financialYear in 2015..2021 -> 0.095
        financialYear == 2022 -> 0.10
        financialYear == 2023 -> 0.105
        financialYear == 2024 -> 0.11
        financialYear == 2025 -> 0.115
        else -> 0.12
    }


//fun incomeTax(income: Double): Double = TODO()


// salary, income / take home pay, living expenses, savings, compulsory super contribution, concessional super contribution,
// non-concessional super contribution, personal principle, super principle

// inflation
// investment returns
// tax on investment returns

// calculate living expenses
//

// super contribution tax
