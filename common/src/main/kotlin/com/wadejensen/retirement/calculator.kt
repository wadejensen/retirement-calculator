package com.wadejensen.retirement

import com.wadejensen.retirement.employment.Employment
import com.wadejensen.retirement.investment.investmentGains
import com.wadejensen.retirement.superannuation.Super
import com.wadejensen.retirement.tax.IncomeTax
import com.wadejensen.retirement.tax.Medicare

data class YearAndSalary(val financialYear: Int, val salary: Double)

//val initialState = YearAndSalary(
//    )

// TODO insert parameters from caller
class RetirementCalculator(
    val age: Int = 23,
    val RETIREMENT_YEAR: Int = 2060,
    val PAY_RISE_RATE: Double = 0.05,
    val MAX_PAY: Double = 150_000.0,
    val STOCK_MARKET_RETURN_RATE: Double = 0.075,
    val INFLATION: Double = 0.03,
    val SUPERANNUATION_RETURN_RATE: Double = 0.05,
    val HAS_PRIVATE_HEALTH_INSURANCE: Boolean = false) {


// Do pre-processing on user data. Get the salary without super etc

    fun calculateLedger(
        initialFinancialYear: Int,
        initialSalary: Double,
        finalFinancialYear: Int,
        retirementYear: Int,
        hasPrivateHealthInsurance: Boolean,
        payRiseRate: Double,
        stockMarketReturn: Double,
        inflation: Double,
        maxPay: Double): Array<LedgerRow> {

        val year1Ledger: LedgerRow =
            calculateNextLedgerRow(
                initialFinancialYear,
                initialSalary,
                investmentPrinciple = 0.0,
                superPrinciple = 0.0,
                hasPrivateHealthInsurance = hasPrivateHealthInsurance)

        val ledger = (initialFinancialYear + 1 .. retirementYear)
            .fold(arrayOf(year1Ledger)) { ledger, _ ->
                ledger + calculateNextLedgerRow(ledger, hasPrivateHealthInsurance)
            }

        // needs to simulate draw down from retirementYear until finalFinancialYear
        ledger.forEach { row -> println(row) }
        return ledger
    }

    private fun calculateNextLedgerRow(
        ledger: Array<LedgerRow>,
        hasPrivateHealthInsurance: Boolean): LedgerRow
    {
        return calculateNextLedgerRow(
            financialYear             = ledger.last().financialYear + 1,
            salary                    = Employment.raiseSalary(ledger.last().salary, PAY_RISE_RATE, MAX_PAY),
            investmentPrinciple       = ledger.last().investmentPrinciple,
            superPrinciple            = ledger.last().superPrinciple,
            hasPrivateHealthInsurance = hasPrivateHealthInsurance)
    }



    private fun calculateNextLedgerRow(
        financialYear: Int,
        salary: Double,
        superPrinciple: Double,
        investmentPrinciple: Double,
        hasPrivateHealthInsurance: Boolean): LedgerRow
    {

        // SUPER
        val compulsorySuperContribution = Super.compulsoryContribution(salary, financialYear)
        val additionalConcessionalSuperContribution: Double = 0.0 // TODO()
        val nonConcessionalSuperContribution: Double = 0.0 // TODO()
        val nonConcessionalSuperContributionTax: Double = 0.0 // TODO()

        val superGains = superPrinciple + Super.annualReturn(superPrinciple, SUPERANNUATION_RETURN_RATE)

        val concessionalSuperTax = Super.concessionalContributionTax(
            superPrinciple,
            compulsorySuperContribution,
            additionalConcessionalSuperContribution)

        val netConcessionalSuperContribution: Double = compulsorySuperContribution +
            additionalConcessionalSuperContribution -
                concessionalSuperTax

        val netSuperContribution = netConcessionalSuperContribution +
            nonConcessionalSuperContribution -
                nonConcessionalSuperContributionTax

        val totalSuperContribution = netConcessionalSuperContribution + nonConcessionalSuperContribution

        val investmentGains = investmentGains(investmentPrinciple, STOCK_MARKET_RETURN_RATE)

        // TAXATION
        val taxDeductions: Double = additionalConcessionalSuperContribution // + TODO()
        val deductions: Double = additionalConcessionalSuperContribution // + TODO()
        val capitalGains = 0.0
        val taxableIncome = IncomeTax.taxableIncome(salary, deductions, capitalGains)
        val incomeTax = IncomeTax.incomeTax(taxableIncome) // TODO get CGT out of it
        val incomeTaxOffsets = IncomeTax.taxOffsets(taxableIncome, incomeTax)
        val medicare = Medicare.tax(taxableIncome, hasPrivateHealthInsurance)

        val takeHomePay = taxableIncome + incomeTaxOffsets - incomeTax - medicare

        return LedgerRow(
            financialYear = financialYear + 1,
            salary = salary,
            taxableIncome = taxableIncome,
            incomeTax = incomeTax,
            medicare = medicare,
            takeHomePay = takeHomePay,
            compulsorySuperContribution = compulsorySuperContribution,
            concessionalSuperTax = concessionalSuperTax,
            // TODO concessional super contribution tax
            netCompulsorySuperContribution = netConcessionalSuperContribution,
            netSuperContribution = totalSuperContribution,
            superGains = superGains,
            superPrinciple = superPrinciple +
                netSuperContribution +
                    superGains,
            investmentGains = investmentGains,
            investmentPrinciple = investmentPrinciple
                + investmentGains
            //  + disposableIncome
        )
    }
}

