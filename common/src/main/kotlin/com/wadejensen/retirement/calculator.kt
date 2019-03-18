package com.wadejensen.retirement

import com.wadejensen.retirement.tax.data.SuperContribution
import com.wadejensen.retirement.tax.data.SuperTax
import com.wadejensen.retirement.validation.ValidationWarning

object Calculator {
  fun calculate(
      financialYear: Int,
      salary: Double,
      salaryIncludesSuper: Boolean,
      capitalGains: Double,
      capitalGainsDiscount: Double,
      hasHealthInsurance: Boolean,
      deductions: Double
  ): Unit {

    val warnings = mutableListOf<ValidationWarning>()

    val salaryAfterCompulsorySuper = normaliseSalary(
      salary = salary,
      salaryIncludesSuper = salaryIncludesSuper,
      financialYear = financialYear
    )

    val compulsorySuper = Super.compulsory(salary, financialYear)

    // TODO super strategies
    val superSalarySacrifice = 0.0
    val afterTaxContribution = 0.0
    val superContribution = SuperContribution(
      compulsory = compulsorySuper,
      pretax = superSalarySacrifice,
      afterTax = afterTaxContribution
    )

    val netCapitalGains = capitalGains - capitalGainsDiscount

    val taxableIncome = IncomeTax.taxableIncome(
      salary = salaryAfterCompulsorySuper,
      superSalarySacrifice = superSalarySacrifice,
      netCapitalGains = netCapitalGains,
      deductions = deductions
    )

    val incomeTaxPayable = IncomeTax.incomeTax(
      taxableIncome = taxableIncome,
      compulsorySuper = compulsorySuper,
      superSalarySacrifice = superSalarySacrifice,
      hasHealthInsurance = hasHealthInsurance
    )

    val superTax = Super.contributionTax(
      compulsoryContribution = compulsorySuper,
      pretaxContribution = superSalarySacrifice,
      afterTaxContribution = afterTaxContribution,
      taxableIncome = taxableIncome,
      warnings = warnings
    )

  }



  /**
   * Normalise salary numbers based on whether the cash salary package figure is
   * inclusive or exclusive of super
   * @param salary Base salary figure
   * @param salaryIncludesSuper Whether or not the salary figure includes
   * compulsory superannuation
   * @param financialYear The current financial year
   */
  internal fun normaliseSalary(
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
