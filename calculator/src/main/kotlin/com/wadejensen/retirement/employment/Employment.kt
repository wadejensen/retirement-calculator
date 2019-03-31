package com.wadejensen.retirement.employment

import com.wadejensen.retirement.Super


object Employment
{
  fun salaryAfterCompulsorySuper(
      salary: Double,
      salaryIncludesSuper: Boolean,
      financialYear: Int
  ): Double {
    return if (salaryIncludesSuper) {
      salary / (1 + Super.compsulsoryContributionRate(financialYear))
    }
    else {
      salary
    }
  }
}

//object Employment {
//  fun payRise(salary: Double, payRiseRate: Double): Double =
//    salary * payRiseRate
//
//  fun raiseSalary(salary: Double, payRiseRate: Double, maxPay: Double) =
//    min(salary + payRise(salary, payRiseRate), maxPay)
//}
