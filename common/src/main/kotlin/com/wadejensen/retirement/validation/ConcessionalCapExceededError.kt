package com.wadejensen.retirement.validation

data class ConcessionalCapExceededError (
    override val message: String,
    val concessionalCap: Double,
    val contributionAmount: Double
) : ValidationWarning()
