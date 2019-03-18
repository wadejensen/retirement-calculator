package com.wadejensen.retirement

import com.wadejensen.retirement.tax.data.IncomeTaxPayable
import com.wadejensen.retirement.tax.data.Portfolio
import com.wadejensen.retirement.tax.data.SuperContribution
import com.wadejensen.retirement.tax.data.SuperTax

data class LedgerRow(
    val financialYear: Int,
    val salary: Double,
    val taxableIncome: Double,
    val incomeTax: IncomeTaxPayable,
    val superContribution: SuperContribution,
    val superTax: SuperTax,
    val takeHomePay: Double,
    val netSuperContribution: Double,
    val portfolio: Portfolio
) {
  fun csv() {
    val fields = arrayOf(
      financialYear.toString(),
      salary.toString(),
      taxableIncome.toString(),
      incomeTax.toString(),
      superContribution.toString(),
      superTax.toString(),
      takeHomePay.toString(),
      netSuperContribution.toString(),
      portfolio.toString()
    )

    println(fields.joinToString(" | "))
  }
}

// has retired
// capital gains medicare??
