package com.wadejensen.retirement

import com.wadejensen.retirement.investment.investmentGains
import com.wadejensen.retirement.superannuation.concessionalSuperContributionTax
import com.wadejensen.retirement.tax.compulsorySuperContribution
import com.wadejensen.retirement.tax.incomeTax
import com.wadejensen.retirement.tax.medicareLevy
import com.wadejensen.retirement.tax.taxableIncome

data class YearAndSalary(val financialYear: Int, val salary: Double)

//val initialState = YearAndSalary(
//    )

// TODO insert parameters from caller
class RetirementCalculator() {
    val financialYear = 2018
    val salary = 100_000.0
    val age = 23


    val RETIREMENT_YEAR = 2060
    val PAY_RISE_RATE = 0.05
    val STOCK_MARKET_RETURN_RATE = 0.075
    val INFLATION = 0.03
    val SUPERANNUATION_RETURN_RATE = 0.05
    val HAS_PRIVATE_HEALTH_INSURANCE = false

    fun payRise(salary: Double, payRiseRate: Double): Double =
        salary * payRiseRate

    fun superReturns(principle: Double, superannuationReturnRate: Double = SUPERANNUATION_RETURN_RATE): Double =
        principle * superannuationReturnRate


// Do pre-processing on user data. Get the salary without super etc

    fun calculateLedger(
        initialFinancialYear: Int,
        initialSalary: Double,
        finalFinancialYear: Double,
        retirementYear: Int,
        hasPrivateHealthInsurance: Boolean,
        payRiseRate: Double,
        stockMarketReturn: Double,
        inflation: Double,
        maxPay: Double): Array<LedgerRow> {

        val years = (initialFinancialYear..retirementYear)
        val emptyLedger = emptyArray<LedgerRow>()
        years.fold(emptyLedger) { ledger, _ -> ledger + calculateNextLedgerRow(ledger.last()) }

        // needs to simulate draw down from retirementYear until finalFinancialYear
        return TODO()
    }

    //fun superContribution(principle: Double, )

    fun calculateNextLedgerRow(
        previous: LedgerRow,
        payRiseRate: Double = PAY_RISE_RATE,
        inflation: Double   = INFLATION,
        stockMarketReturnRate: Double  = STOCK_MARKET_RETURN_RATE,
        hasPrivateHealthInsurance: Boolean = HAS_PRIVATE_HEALTH_INSURANCE): LedgerRow {

        val salary = previous.salary + payRise(previous.salary, payRiseRate)
        val compulsorySuperContribution = compulsorySuperContribution(salary, previous.financialYear)

        val additionalConcessionalSuperContribution: Double = TODO()
        val nonConcessionalSuperContribution: Double = TODO()

        val superGains = previous.superPrinciple + superReturns(previous.superPrinciple)

        val concessionalSuperTax = concessionalSuperContributionTax(
            previous.superPrinciple,
            compulsorySuperContribution,
            additionalConcessionalSuperContribution)

        val netConcessionalSuperContribution: Double = compulsorySuperContribution +
            additionalConcessionalSuperContribution - concessionalSuperTax

        val totalSuperContribution = netConcessionalSuperContribution + nonConcessionalSuperContribution



        val superPrinciple = previous.superPrinciple +
            superGains +
            additionalConcessionalSuperContribution +
            nonConcessionalSuperContribution


        val investmentGains = investmentGains(previous.investmentPrinciple, STOCK_MARKET_RETURN_RATE)
        val investmentPrincple = previous.investmentPrinciple + investmentGains

        val taxDeductions: Double = additionalConcessionalSuperContribution //+ TODO()

        val deductions: Double = additionalConcessionalSuperContribution //+ TODO()

        val capitalGains = 0.0

        val taxableIncome = taxableIncome(salary, deductions, capitalGains)

        val incomeTax = incomeTax(taxableIncome) // TODO get CGT out of it

        val medicareLevy = medicareLevy(incomeTax, hasPrivateHealthInsurance)

        val compulsorySuperContributionTax: Double = TODO()

        return LedgerRow(
            financialYear = previous.financialYear + 1,
            salary = salary,
            taxableIncome = taxableIncome,
            incomeTax = incomeTax,
            medicareLevy = medicareLevy,
            takeHomePay = TODO(),
            compulsorySuperContribution = compulsorySuperContribution,
            compulsorySuperContributionTax = compulsorySuperContributionTax,
            // TODO concessional super contribution tax
            netCompulsorySuperContribution = netConcessionalSuperContribution,
            investmentPrinciple = investmentPrincple,
            investmentGains = investmentGains,
            superPrinciple = superPrinciple,
            superGains = superGains
        )
    }
}

