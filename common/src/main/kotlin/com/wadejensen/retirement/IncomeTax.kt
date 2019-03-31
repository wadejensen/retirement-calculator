package com.wadejensen.retirement

import kotlin.math.max
import kotlin.math.min
import kotlin.math.round
import com.wadejensen.retirement.tax.data.IncomeTaxPayable
import com.wadejensen.retirement.validation.MedicareLevyWarning
import com.wadejensen.retirement.validation.ValidationWarning

/**
 * Calculates the income tax owed
 * https://www.paycalculator.com.au/
 */
object IncomeTax {

  private val TAX_FREE_THRESHOLD = 18_200.0
  private val LOW_INCOME_THRESHOLD = 37_000.0
  private val MIDDLE_INCOME_THRESHOLD = 90_000.0
  private val HIGH_INCOME_THRESHOLD = 180_000.0

  private val MEDICARE_LEVY_RATE = 0.02

  fun incomeTax(
      taxableIncome: Double,
      compulsorySuper: Double,
      superSalarySacrifice: Double,
      hasHealthInsurance: Boolean,
      warnings: MutableList<ValidationWarning>
  ): IncomeTaxPayable {
    // income for medicare surcharge purposes
    val medicareIncome = medicareIncome(
      taxableIncome = taxableIncome,
      pretaxSuperContribution = compulsorySuper + superSalarySacrifice
    )

    val incomeTax = baseIncomeTax(taxableIncome)
    val medicare = medicare(medicareIncome, hasHealthInsurance, warnings)
    val taxOffset = taxOffsets(taxableIncome, incomeTax)

    return IncomeTaxPayable(incomeTax, medicare, taxOffset)
  }

  /**
   * Calculate income for base income tax purposes
   *
   * @param salary income from employment after compulsory and voluntary
   * superannuation contributions and before tax
   * @param superSalarySacrifice Super contributed pretax in excess of
   * compulsory contribution
   * @param netCapitalGains income made from selling assets, minus capital gains
   * discount. This is typically a 50% discount on profit made from selling an
   * asset, if the asset is held for more than 12 months.
   * @param deductions Any tax deductions (money spent in the process of making
   * a living)
   */
  internal fun taxableIncome(
      salary: Double,
      superSalarySacrifice: Double,
      netCapitalGains: Double,
      deductions: Double
  ): Double {
    return salary + netCapitalGains - superSalarySacrifice - deductions
  }

  fun baseIncomeTax(income: Double): Double {
    val incomeTax = when {
      income <= TAX_FREE_THRESHOLD ->
        0.0
      income > TAX_FREE_THRESHOLD && income <= LOW_INCOME_THRESHOLD ->
        0.19 * (income - TAX_FREE_THRESHOLD)
      income > LOW_INCOME_THRESHOLD && income <= MIDDLE_INCOME_THRESHOLD ->
        3_572.0 + 0.325 * (income - LOW_INCOME_THRESHOLD)
      income > MIDDLE_INCOME_THRESHOLD && income <= HIGH_INCOME_THRESHOLD ->
        20_797.0 + 0.37 * (income - MIDDLE_INCOME_THRESHOLD)
      else ->
        54_097.0 + 0.45 * (income - HIGH_INCOME_THRESHOLD)
    }
    return round(incomeTax)
  }

  /**
   * Income for Medicare levy purposes
   * https://www.ato.gov.au/individuals/medicare-levy/medicare-levy-surcharge/income-for-medicare-levy-surcharge-purposes/
   */
  internal fun medicareIncome(
      taxableIncome: Double,
      pretaxSuperContribution: Double
  ): Double {
    return taxableIncome + pretaxSuperContribution
  }

  /**
   * Taxes to pay for Medicare
   * @param income Income for medicare levy surcharge purposes
   * @param hasHealthInsurance True if user has private hospital cover
   */
  fun medicare(
      income: Double, hasHealthInsurance: Boolean,
      warnings: MutableList<ValidationWarning>
  ): Double {
    val surchargeRate = levySurchargeRate(income, hasHealthInsurance)

    if (surchargeRate != 0.0) {
      warnings.add(
        MedicareLevyWarning(
           message = """Medicare levy surcharge applied since middle taxable
             income bracket exceeded and no private hospital cover.""",
           surchargeRate = surchargeRate,
           surchargeIncomeThreshold = MIDDLE_INCOME_THRESHOLD
        )
      )
    }
    return income * (MEDICARE_LEVY_RATE + surchargeRate)
  }

  /**
   * Medicare levy surcharge for middle and high income earners who do not have
   * private hospital coverage.
   * @param income Income for medicare levy surcharge purposes
   * @param hasHealthInsurance Whether or not the user has valid hospital cover
   */
  internal fun levySurchargeRate(
      income: Double,
      hasHealthInsurance: Boolean
  ): Double {
    return when {
        hasHealthInsurance || income <= MIDDLE_INCOME_THRESHOLD -> 0.0
        income > MIDDLE_INCOME_THRESHOLD && income <= 105_000.0 -> 0.01
        income > 105_000.0 && income <= 140_000.0 -> 0.0125
        else -> 0.035
    }
  }

  fun taxOffsets(income: Double, incomeTax: Double): Double {
    val lowIncomeOffset = lowIncomeTaxOffset(income)
    val lowAndMiddleIncomeOffset = lowAndMiddleIncomeTaxOffset(income)

    // round to nearest cent
    val possibleTaxOffsets = round((lowAndMiddleIncomeOffset + lowIncomeOffset) * 100.0) / 100.0

    // avoid a medicare offset causing a medicare refund
    return min(incomeTax, possibleTaxOffsets)
  }

  private fun lowIncomeTaxOffset(income: Double): Double {
    return if (income <= 37_000) {
        445.0
    } else {
        val taxOffsetReduction = 0.015 * (income - 37_000)
        max(0.0, 445.0 - taxOffsetReduction)
    }
  }

  private fun lowAndMiddleIncomeTaxOffset(income: Double): Double {
    return if (income <= 37_000) {
        200.0
    } else if (income > 37_000 && income <= 48_000) {
        200 + 0.03 * (income - 37_000.0)
    } else if (income > 48_000.0 && income <= 90_000.0) {
        530.0
    } else {
        val offsetReduction = 0.015 * (income - 90_000.0)
        max(0.0, 530.0 - offsetReduction)
    }
  }
}
