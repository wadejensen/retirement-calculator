package com.wadejensen.retirement.validation

data class MedicareLevyWarning (
    override val message: String,
    val surchargeRate: Double,
    val surchargeIncomeThreshold: Double
) : ValidationWarning()
