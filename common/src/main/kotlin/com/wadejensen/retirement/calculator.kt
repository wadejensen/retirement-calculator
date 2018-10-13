package com.wadejensen.retirement

import com.wadejensen.retirement.employment.payRise
import com.wadejensen.retirement.investment.investmentGains
import com.wadejensen.retirement.superannuation.concessionalSuperContributionTax
import com.wadejensen.retirement.superannuation.superReturns
import com.wadejensen.retirement.tax.*
import kotlin.math.min

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

        val years = (initialFinancialYear + 1 .. retirementYear)
        val emptyLedger = arrayOf(
            LedgerRow(
                financialYear = initialFinancialYear,
                salary = initialSalary,
                taxableIncome = 0.0,
                incomeTax = 0.0,
                medicare = 0.0,
                takeHomePay = 0.0,
                compulsorySuperContribution = 0.0,
                concessionalSuperTax = 0.0,
                netCompulsorySuperContribution = 0.0,
                netSuperAnnuation = 0.0,
                investmentPrinciple = 0.0,
                investmentGains = 0.0,
                superPrinciple = 0.0,
                superGains = 0.0
            ))

        val ledger = years.fold(emptyLedger) { ledger, _ -> ledger + calculateNextLedgerRow(ledger.last(), maxPay = maxPay) }

        // needs to simulate draw down from retirementYear until finalFinancialYear
        ledger.forEach { row -> println(row) }
        return ledger
    }

    //fun superContribution(principle: Double, )

    fun calculateNextLedgerRow(
        previous: LedgerRow,
        payRiseRate: Double = PAY_RISE_RATE,
        inflation: Double   = INFLATION,
        stockMarketReturnRate: Double  = STOCK_MARKET_RETURN_RATE,
        hasPrivateHealthInsurance: Boolean = HAS_PRIVATE_HEALTH_INSURANCE,
        maxPay: Double): LedgerRow {

        val salary = min(previous.salary + payRise(previous.salary, payRiseRate), maxPay)
        val compulsorySuperContribution = compulsorySuperContribution(salary, previous.financialYear)

        val additionalConcessionalSuperContribution: Double = 0.0 // TODO()
        val nonConcessionalSuperContribution: Double = 0.0 // TODO()

        val superGains = previous.superPrinciple + superReturns(previous.superPrinciple, SUPERANNUATION_RETURN_RATE)

        val concessionalSuperTax = concessionalSuperContributionTax(
            previous.superPrinciple,
            compulsorySuperContribution,
            additionalConcessionalSuperContribution)

        val netConcessionalSuperContribution: Double = compulsorySuperContribution +
            additionalConcessionalSuperContribution - concessionalSuperTax

        val totalSuperContribution = netConcessionalSuperContribution + nonConcessionalSuperContribution

        val superPrinciple = previous.superPrinciple + totalSuperContribution + superGains


        val investmentGains = investmentGains(previous.investmentPrinciple, STOCK_MARKET_RETURN_RATE)
        val investmentPrincple = previous.investmentPrinciple + investmentGains

        val taxDeductions: Double = additionalConcessionalSuperContribution // + TODO()

        val deductions: Double = additionalConcessionalSuperContribution // + TODO()

        val capitalGains = 0.0

        val taxableIncome = taxableIncome(salary, deductions, capitalGains)

        val incomeTax = incomeTax(taxableIncome) // TODO get CGT out of it
        val takeHomePay = taxableIncome - incomeTax

        val medicare = medicare(taxableIncome, hasPrivateHealthInsurance)

        return LedgerRow(
            financialYear = previous.financialYear + 1,
            salary = salary,
            taxableIncome = taxableIncome,
            incomeTax = incomeTax,
            medicare = medicare,
            takeHomePay = takeHomePay,
            compulsorySuperContribution = compulsorySuperContribution,
            concessionalSuperTax = concessionalSuperTax,
            // TODO concessional super contribution tax
            netCompulsorySuperContribution = netConcessionalSuperContribution,
            netSuperAnnuation = totalSuperContribution,
            investmentPrinciple = investmentPrincple,
            investmentGains = investmentGains,
            superPrinciple = superPrinciple,
            superGains = superGains
        )
    }
}

