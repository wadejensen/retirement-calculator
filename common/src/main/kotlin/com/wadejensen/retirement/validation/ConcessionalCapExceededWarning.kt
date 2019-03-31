package com.wadejensen.retirement.validation

data class ConcessionalCapExceededWarning (
    override val message: String,
    val concessionalCap: Double,
    val contributionAmount: Double
) : ValidationWarning()
