package com.wadejensen.retirement

import com.wadejensen.retirement.validation.ConcessionalCapExceededWarning
import com.wadejensen.retirement.validation.SuperContributionCapExceededError
import com.wadejensen.retirement.validation.ValidationWarning
//import kotlin.test.Test
//import kotlin.test.assertEquals

import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue

class SuperTest
{
  @Test
  fun testCompulsoryContribution()
  {
    val compulsoryContribution = Super.compulsory(
      salary = 100_000.0,
      financialYear = 2022
    )
    assertEquals(10_000.0, compulsoryContribution, 0.0)
  }

  @Test
  fun testCompsulsoryContributionRate()
  {
    val expectedContributionRates: Map<Int, Double> = mapOf(
      2013 to 0.090,
      2014 to 0.0925,
      2015 to 0.095,
      2016 to 0.095,
      2017 to 0.095,
      2018 to 0.095,
      2019 to 0.095,
      2020 to 0.095,
      2021 to 0.095,
      2022 to 0.100,
      2023 to 0.105,
      2024 to 0.110,
      2025 to 0.115,
      2026 to 0.120
    )

    val contributionRates: Map<Int, Double> = (2013 .. 2026).map {
        year -> year to Super.compsulsoryContributionRate(year)
    }.toMap()
    assertEquals(expectedContributionRates, contributionRates)
  }

  @Test
  fun testConcessionalContributionTax_WhenBelowConcessionalCap()
  {
    val validationWarnings: MutableList<ValidationWarning> = mutableListOf()
    val concessionalContribution = Super.concessionalContributionTax(
      beforeTaxContribution = 15_000.0,
      warnings = validationWarnings
    )

    assertEquals(0.15 * 15_000.0, concessionalContribution, 0.0)
    assertEquals(mutableListOf<ValidationWarning>(), validationWarnings)
  }

  @Test
  fun testConcessionalContributionTax_WhenAboveConcessionalCap()
  {
    val validationWarnings: MutableList<ValidationWarning> = mutableListOf()
    val concessionalContributionTax = Super.concessionalContributionTax(
      beforeTaxContribution = 35_000.0,
      warnings = validationWarnings
    )

    assertEquals(0.15 * 25_000.0 + 0.47 * 10_000.0, concessionalContributionTax, 0.0)
    assertEquals(1, validationWarnings.size)
    assertTrue(validationWarnings[0] is ConcessionalCapExceededWarning)
    val warning = validationWarnings[0] as ConcessionalCapExceededWarning

    assertEquals(35_000.0, warning.contributionAmount, 0.0)
    assertEquals(25_000.0, warning.concessionalCap, 0.0)
  }

  @Test
  fun testNonConcessionalContributionTax_WhenBelowNonCencessionalCap()
  {
    val validationWarnings: MutableList<ValidationWarning> = mutableListOf()
    val nonConcessionalContributionTax = Super.nonConcessionalContributionTax(
      afterTaxContribution = 80_000.0,
      warnings = validationWarnings
    )

    assertEquals(0.0, nonConcessionalContributionTax, 0.0)
    assertTrue(validationWarnings.isEmpty())
  }

  @Test
  fun testNonConcessionalContributionTax_WhenAboveNonConcessionalCap()
  {
    val validationWarnings: MutableList<ValidationWarning> = mutableListOf()
    val nonConcessionalContributionTax = Super.nonConcessionalContributionTax(
      afterTaxContribution = 120_000.0,
      warnings = validationWarnings
    )

    assertEquals((120_000.0 - 100_000.0) * 0.47, nonConcessionalContributionTax, 0.001)

    assertEquals(1, validationWarnings.size)
    assertTrue(validationWarnings[0] is SuperContributionCapExceededError)

    val warning = validationWarnings[0] as SuperContributionCapExceededError
    assertEquals(120_000.0, warning.afterTaxContributionAmount, 0.0)
    assertEquals(100_000.0, warning.superContributionCap, 0.0)
  }

  @Test
  fun testDivision293Tax_WhenBelowThreshold()
  {
    val division293Tax = Super.division293Tax(taxableIncome = 100_000.0)
    assertEquals(0.0, division293Tax, 0.0)
  }

  @Test
  fun testDivision293Tax_WhenAboveThreshold()
  {
    val division293Tax = Super.division293Tax(taxableIncome = 250_001.0)
    assertEquals(0.15 * 25_000.0, division293Tax, 0.0)
  }

  @Test
  fun testDivision293Tax_IsConstant()
  {
    val division293Tax = Super.division293Tax(taxableIncome = 300_000.0)
    assertEquals(0.15 * 25_000.0, division293Tax, 0.0)
  }

  @Test
  fun testContributionTax_E2EWarningAccumulation()
  {
    val warnings: MutableList<ValidationWarning> = mutableListOf()

    Super.contributionTax(
      compulsoryContribution = 10_000.0,
      pretaxContribution = 15_001.0,
      afterTaxContribution = 100_001.0,
      taxableIncome = 100_000.0,
      warnings = warnings
    )

    assertEquals(2, warnings.size)
  }
}
