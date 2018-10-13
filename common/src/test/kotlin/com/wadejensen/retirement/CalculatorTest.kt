package com.wadejensen.retirement

import kotlin.test.Test
import kotlin.test.assertTrue

class CalculatorTest {
    @Test
    fun ensure_salary_is_capped_at_max() {
        val maxPay = 154_000.0

        val ledger = RetirementCalculator().calculateLedger(
            initialFinancialYear = 2018,
            initialSalary = 120_000.0,
            finalFinancialYear = 2060,
            retirementYear = 2040,
            hasPrivateHealthInsurance = false,
            stockMarketReturn = 0.075,
            payRiseRate = 0.05,
            inflation = 0.03,
            maxPay = maxPay)

        val actualMaxPay = ledger
            .map { row -> row.salary }
            .max()!!

        assertTrue(actualMaxPay <= maxPay)
    }
}
