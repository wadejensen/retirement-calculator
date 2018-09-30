package com.wadejensen.retirement

// TODO param docs
data class LedgerRow(
    val financialYear: Int,
    val salary:        Double,
    val taxableIncome: Double,
    val incomeTax:     Double,
    val medicareLevy:  Double,
    val takeHomePay:   Double,
    val compulsorySuperContribution:    Double,
    val compulsorySuperContributionTax: Double,
    val netCompulsorySuperContribution: Double,
    val investmentPrinciple: Double,
    val investmentGains: Double,
    val superPrinciple: Double,
    val superGains: Double)

// has retired
// capital gains tax??
