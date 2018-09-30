package com.wadejensen.retirement.tax

fun taxableIncome(salary: Double, deductions: Double, capitalGains: Double): Double {
    return TODO()
}

/**
 * @param income Taxable income
 * @return Amount of income tax owed
 */
fun incomeTax(income: Double): Double =
    when {
        income <= 18_200.0 -> 0.0
        income > 18_200.0 && income <=  37_000.0 ->            0.19  * (income - 18_200.0)
        income > 37_000.0 && income <=  90_001.0 -> 3_572.0  + 0.325 * (income - 37_000.0)
        income > 90_000.0 && income <= 180_000.0 -> 20_797.0 + 0.37  * (income - 90_000.0)
        else -> 54_097.0 + 0.45 * (income - 180_000)
    }

/**
 * @param income Income before tax (must be greater than zero)
 * @param hasPrivateHealthInsurance
 * @return Amount of tax owed due to medicare levy
 */
fun medicareLevy(income: Double, hasPrivateHealthInsurance: Boolean): Double {
    val taxRate = when {
        income <= 0.0      -> 0.0
        hasPrivateHealthInsurance -> 0.02
        income <= 90_000.0 -> 0.02
        income >  90_000.0 && income <= 105_000.0 -> 0.03
        income > 105_000.0 && income <= 140_000.0 -> 0.0325
        else -> 0.035
    }
    return income * taxRate
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
