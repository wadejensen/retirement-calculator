package com.wadejensen.retirement

import com.typesafe.config.ConfigFactory
import io.github.config4k.*

import com.wadejensen.retirement.tax.incomeTax

fun main(args : Array<String>) {
    println("Hello, world!")

//    println(incomeTax(100.00))
//
//
//    data class Person(val name: String, val age: Int)
//    data class Family(val list: List<Person>)
//
//    // typesafe config supports not only HOCON but also JSON
//    // HOCON(Human-Optimized Config Object Notation) is the JSON superset
//    val config = ConfigFactory.parseResources("test.conf")
//
//    // typesafe config + config4k
//    val x = config.extract<Family>("family")
//
//    println(x)

    RetirementCalculator().calculateLedger(
        initialFinancialYear = 2018,
        initialSalary = 120_000.0,
        finalFinancialYear = 2060,
        retirementYear = 2040,
        hasPrivateHealthInsurance = false,
        stockMarketReturn = 0.075,
        payRiseRate = 0.05,
        inflation = 0.03,
        maxPay = 154_000.0)
}
