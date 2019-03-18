package com.wadejensen.retirement

import kotlin.test.Test
import kotlin.test.assertEquals

class CalculatorTest
{
  @Test
  fun testNormaliseSalary_WhenInclusiveOfSuper()
  {
    val normalisedSalary = Calculator.normaliseSalary(
      salary = 99_000.0,
      salaryIncludesSuper = true,
      financialYear = 2020
    )

    assertEquals(90_000.0, normalisedSalary)
  }

  @Test
  fun testNormaliseSalary_WhenExclusiveOfSuper()
  {
    val normalisedSalary = Calculator.normaliseSalary(
      salary = 99_000.0,
      salaryIncludesSuper = true,
      financialYear = 2020
    )

    assertEquals(99_000.0, normalisedSalary)
  }
}
