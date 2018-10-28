package com.wadejensen.retirement.superannuation

object Super {
    fun annualReturn(principle: Double, superannuationReturnRate: Double): Double
    {
        return principle * superannuationReturnRate
    }

    fun concessionalContributionTax(
        principle: Double,
        compulsoryConcessionalContribution: Double,
        nonCompulsoryConcessionalContribution: Double): Double
    {

        // if happy case 25000 (concessional)

        // if happy case 180000 (non-concessional)

        println("concessionalSuperContributionTax not implemented")

        // if income + super contribution <= 250,000 then tax rate =  0.15
        // if income + super contribution > 250,000 then tax rate =  0.3

        return 0.0
    }

    fun applySuperCapChecks(
        netConcessionalSuperContribution: Double,
        nonConcessionalSuperContribution: Double): Double = TODO()


    /**
     * @param salary Cash salary package non-inclusive of super
     * @param financialYear The financial year for tax purposes
     * @return Compulsory employer superannuation contribution amount
     */
    fun compulsoryContribution(salary: Double, financialYear: Int): Double
    {
        return salary * compsulsoryContributionRate(financialYear)
    }

     fun compsulsoryContributionRate(financialYear: Int): Double
     {
        return when {
            financialYear <= 2013 -> 0.09
            financialYear == 2014 -> 0.0925
            financialYear in 2015..2021 -> 0.095
            financialYear == 2022 -> 0.10
            financialYear == 2023 -> 0.105
            financialYear == 2024 -> 0.11
            financialYear == 2025 -> 0.115
            else -> 0.12
        }
    }
}

//data class ConcessionalSuperTax(val compulsoryContributionTax: Double, val nonCompulsoryContributionTax: Double)
