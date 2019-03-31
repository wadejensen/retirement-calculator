package com.wadejensen.retirement;

public class Main {
  fun main(args: Array<String>) {
    println("Hello, world!")

    Calculator.calculate(
      financialYear = 2020,
      salary = 100_000.0,
      salaryIncludesSuper = false,
      capitalGains = 0.0,
      capitalGainsDiscount = 0.0,
      hasHealthInsurance = true,
      deductions = 0.0
    )
  }
}
