package com.wadejensen.retirement

//import kotlin.test.Test
//import kotlin.test.assertEquals

import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue

import org.junit.runner.RunWith
import org.junit.runners.JUnit4


class CalculatorTest
{
  @Test
  fun testNormaliseSalary_WhenExclusiveOfSuper()
  {
    val normalisedSalary = Calculator.normaliseSalary(
      salary = 99_000.0,
      salaryIncludesSuper = false,
      financialYear = 2022
    )

    assertEquals(99_000.0, normalisedSalary, 0.0)
  }

  @Test
  fun testNormaliseSalary_WhenInclusiveOfSuper()
  {
    val normalisedSalary = Calculator.normaliseSalary(
      salary = 99_000.0,
      salaryIncludesSuper = true,
      financialYear = 2022
    )

    assertEquals(90_000.0, normalisedSalary, 0.0)
  }
}
