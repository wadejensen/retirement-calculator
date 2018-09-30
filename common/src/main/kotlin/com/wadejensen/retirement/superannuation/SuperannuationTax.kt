package com.wadejensen.retirement.superannuation

data class ConcessionalSuperTax(val compulsoryContributionTax: Double, val nonCompulsoryContributionTax: Double)


fun concessionalSuperContributionTax(
    principle: Double,
    compulsoryConcessionalContribution: Double,
    nonCompulsoryConcessionalContribution: Double): Double {

    return TODO()
}


fun applySuperCapChecks(
    netConcessionalSuperContribution: Double,
    nonConcessionalSuperContribution: Double): Double = TODO()


