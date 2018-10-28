package com.wadejensen.retirement.employment

import com.wadejensen.retirement.superannuation.Super
import kotlin.math.min

object Employment {
    fun salaryExcludingSuper(financialYear: Int, salaryIncludesSuper: Boolean, salary: Double) =
        if (salaryIncludesSuper)
            salary / (1 + Super.compsulsoryContributionRate(financialYear))
        else
            salary

    fun payRise(salary: Double, payRiseRate: Double): Double =
        salary * payRiseRate

    fun raiseSalary(salary: Double, payRiseRate: Double, maxPay: Double) =
        min(salary + payRise(salary, payRiseRate), maxPay)
}
