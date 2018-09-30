package com.wadejensen.retirement.employment

import com.wadejensen.retirement.tax.compsulsorySuperRate
import com.wadejensen.retirement.tax.medicareLevy

//class EmploymentIncome {
//    fun salary()
//}


val salaryIncludesSuper = false
val hasPrivateHeathInsurance = false

val salary = 100_000.0
val financialYear = 2018

fun salaryExcludingSuper(financialYear: Int, salaryIncludesSuper: Boolean, salary: Double) =
    if (salaryIncludesSuper)
        salary / (1 + compsulsorySuperRate(financialYear))
    else
        salary



fun taxDeductions(): Double = 0.0


//val salaryExludingSuper = salaryExcludingSuper(financialYear, salaryIncludesSuper, salary)
//val taxDeductions       = taxDeductions()
//
//fun interest(): Double = 0.0
//fun investmentInterest(investmentReturnRate: Double, investmentPrinciple: Double) =
//    investmentReturnRate * investmentPrinciple
//
//fun capitalGains(): Double = 0.0
//
//val taxableIncome = salaryExludingSuper - taxDeductions
//
//val incomeTax    = incomeTax(taxableIncome)
//val medicareLevy = medicareLevy(taxableIncome, hasPrivateHeathInsurance)
//
//val takeHomePay = taxableIncome - incomeTax - medicareLevy
