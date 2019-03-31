package com.wadejensen.retirement.validation

data class SuperContributionCapExceededError(
    override val message: String,
    val superContributionCap: Double,
    val afterTaxContributionAmount: Double
) : ValidationWarning()
