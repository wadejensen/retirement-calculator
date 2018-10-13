package com.wadejensen.retirement.employment

import com.wadejensen.retirement.tax.compsulsorySuperRate
import kotlin.math.min

//class EmploymentIncome {
//    fun salary()
//}


val salaryIncludesSuper = false
val hasPrivateHeathInsurance = false

val salary = 100_000.0
val financialYear = 2018

fun salaryExcludingSuper(financialYear: Int, salaryIncludesSuper: Boolean, salary: Double) =
    if (salaryIncludesSuper)
        salary / (1 + compsulsorySuperRate(financialYear))
    else
        salary

fun payRise(salary: Double, payRiseRate: Double): Double =
    salary * payRiseRate


fun taxDeductions(): Double = 0.0
