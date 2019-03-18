package com.wadejensen.retirement

import com.wadejensen.retirement.tax.data.SuperTax
import com.wadejensen.retirement.validation.ConcessionalCapExceededError
import com.wadejensen.retirement.validation.SuperContributionCapExceededError
import com.wadejensen.retirement.validation.ValidationWarning

object Super {

  val CONCESSIONAL_CAP = 25_000.0
  val TAX_RATE = 0.15
  val MAX_INCOME_TAX_RATE = 0.47

  val NON_CONCESSIONAL_CAP = 100_000.0
  val DIVISION_293_THRESHOLD = 250_000.0

  fun compulsory(salary: Double, financialYear: Int): Double =
    salary * compsulsoryContributionRate(financialYear)

  /**
   * https://www.superguide.com.au/boost-your-superannuation/superannuation-guarantee-rate
   */
  fun compsulsoryContributionRate(financialYear: Int): Double {
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

  /**
   * Calculates taxation incurred on Superannuation contributions.
   * Super contributions are typically taxed at 15%, unless certain
   * contribution caps are exceeded.
   * @param compulsoryContribution The required base super contribution amount,
   * typically ~10% of salary.
   * @param pretaxContribution Any super contribution made before tax or which
   * is covered for a tax rebate, compulsory + pretax < $25,000.
   * @param afterTaxContribution Any additional non-concessional super
   * contribution on which income tax has already been paid.
   * @param warnings A accumulator to be mutated to add any warnings that
   * should be surfaced to the user on calculation output.
   */
  fun contributionTax(
      compulsoryContribution: Double,
      pretaxContribution: Double,
      afterTaxContribution: Double,
      taxableIncome: Double,
      warnings: MutableList<ValidationWarning>
  ): SuperTax {

    val beforeTaxContribution = compulsoryContribution + pretaxContribution
    val concessional: Double = concessionalContributionTax(
      beforeTaxContribution,
      warnings
    )
    val nonConcessional = nonConcessionalContributionTax(
      afterTaxContribution,
      warnings
    )
    val division293 = division293Tax(taxableIncome)

    return SuperTax(concessional, nonConcessional, division293)
  }

  /**
   * What is the concessional component of super tax? ie. pretax contribution
   * (should be 15% unless exceeding 25k cap)
   */
  internal fun concessionalContributionTax(
      beforeTaxContribution: Double,
      warnings: MutableList<ValidationWarning>
  ): Double {
      return if (beforeTaxContribution <= CONCESSIONAL_CAP) {
        beforeTaxContribution * TAX_RATE
      }
      else {
        val warning = ConcessionalCapExceededError(
          message = """
        Concessional super cap has been exceeded.
        Some of your contribution will be taxed at the maximum income tax rate.
        """.trimIndent(),
          concessionalCap = CONCESSIONAL_CAP,
          contributionAmount = beforeTaxContribution
        )
        warnings.add(warning)

        val concessionalTaxes = TAX_RATE * CONCESSIONAL_CAP
        val excessContributionTaxes =
            MAX_INCOME_TAX_RATE * (beforeTaxContribution - CONCESSIONAL_CAP)
        concessionalTaxes + excessContributionTaxes
      }
  }

  /**
   * What is the non-concessional component? Contributions made post-tax.
   * (should be zero unless exceeding 100k cap)
   * https://www.ato.gov.au/Individuals/Super/In-detail/Growing-your-super/Super-contributions---too-much-can-mean-extra-tax/?page=3#Non_concessional_contributions
   */
  internal fun nonConcessionalContributionTax(
      afterTaxContribution: Double,
      warnings: MutableList<ValidationWarning>
  ): Double {
    return if (afterTaxContribution <= NON_CONCESSIONAL_CAP)
      0.0
    else {
      val warning = SuperContributionCapExceededError(
        message = """
          After tax super contribution cap has been exceeded.
          Some of your contribution will be taxed at the max income tax rate.
          """.trimIndent(),
        superContributionCap = NON_CONCESSIONAL_CAP,
        afterTaxContributionAmount = afterTaxContribution
      )
      warnings.add(warning)

      MAX_INCOME_TAX_RATE * (afterTaxContribution - NON_CONCESSIONAL_CAP)
    }
  }

  /**
   * What is the Division 293 (rich people's) component?
   * (should be 0% unless earning more than 250k)
   * (should increase tax on concessional component to 30%)
   * https://www.ato.gov.au/Individuals/Super/In-detail/Growing-your-super/Division-293-tax---information-for-individuals/
   */
  internal fun division293Tax(
      taxableIncome: Double
  ): Double {
    return if (taxableIncome > DIVISION_293_THRESHOLD)
      0.15 * CONCESSIONAL_CAP
    else
      0.0
  }
}
