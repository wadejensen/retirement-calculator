package com.wadejensen.retirement

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

object MaximumSuper {

    /**
     * Calculates the maximum valid before tax super contribution without incurring penalties.
     * @param compulsoryContribution The amount of compulsory superannuation contribution made by
     * an employer.
     * @param taxableIncome The taxable income, less tax deductions
     * @param annualExpenses Expected annual spending on essential and discretionary expenses
     */
//    fun beforeTaxVoluntarySuperContribution(
//        salaryExclusive: Double,
//        taxableIncome: Double,
//        annualExpenses: Double
//    ): Double {
//        val beforeTaxCapRemaining = 25_000.0 - salaryExclusive
//        val minSavings = disposableIncome(taxableIncome, beforeTaxCapRemaining) - annualExpenses
//        val maxSavings = disposableIncome(taxableIncome, 0.0) - annualExpenses
//
//        return if (maxSavings <= 0.0) {
//            // We can't afford to pay into super as we're already overspending
//            0.0
//        } else if (minSavings >= 0.0) {
//            // We can afford to pay the max pretax super amount
//            beforeTaxCapRemaining
//        } else if (minSavings < 0.0) {
//            return findRootByBisection(
//                f = { pretaxContribution: Double ->
//                    disposableIncome(taxableIncome, pretaxContribution) - annualExpenses },
//                high = beforeTaxCapRemaining,
//                low = 0.0,
//                tolerance = 1e-2,
//                iter = 20)
//        } else {
//            throw IllegalStateException("Invalid disposable income calculation")
//        }
//    }

//    fun findRootByBisection(
//        f: (Double) -> Double,
//        high: Double,
//        low: Double,
//        tolerance: Double,
//        iter: Int = 20
//    ): Double {
//        val x = low + (high - low) / 2.0
//        val fx = f(x)
//        if (iter == 0 || abs(fx) <= tolerance ) {
//            return fx
//        }
//        else {
//            findRootByBisection(f, max(x, high), min(x, low), tolerance, iter - 1)
//        }
//    }
//
//    private fun disposableIncome(
//        taxableIncome: Double,
//        pretaxContribution: Double
//    ): Double {
//        val taxableIncome = IncomeTaxPayable.taxableIncome(salary, deductions, capitalGains)
//
//        val expectedTax = IncomeTaxPayable.fromSalary(taxableIncome - pretaxContribution)
//        return taxableIncome - expectedTax
//    }

//    fun afterTaxSuperContribution(compolsoraryContribution: Double, taxableIncome: Double, annualExpenses: Double): Double {
//        TODO("not implemented")
//    }
//
//    fun investmentContribution(compolsoraryContribution: Double, taxableIncome: Double, annualExpenses: Double): Double {
//        TODO("not implemented")
//    }

}
