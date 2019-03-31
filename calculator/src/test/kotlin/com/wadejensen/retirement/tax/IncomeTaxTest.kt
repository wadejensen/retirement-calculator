package com.wadejensen.retirement.tax

import com.wadejensen.retirement.IncomeTax
import com.wadejensen.retirement.tax.data.IncomeTaxPayable
import com.wadejensen.retirement.validation.ValidationWarning
//import kotlin.test.Test
//import kotlin.test.assertEquals

import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue

import org.junit.runner.RunWith
import org.junit.runners.JUnit4

class IncomeTaxTest {
  /** BaseIncomeTaxTest **/
  @Test
  fun negativeIncome()
  {
    val taxableIncome = -10_000.0
    val incomeTax = IncomeTax.baseIncomeTax(taxableIncome)
    assertEquals(0.0, incomeTax, 0.0)
  }

  @Test
  fun zeroIncome()
  {
      val taxableIncome = 0.0
      assertEquals(0.0, IncomeTax.baseIncomeTax(taxableIncome), 0.0)
  }

  @Test
  fun withinTaxFreeThreshold()
  {
      val taxableIncome = 10_000.0
      assertEquals(0.0, IncomeTax.baseIncomeTax(taxableIncome), 0.0)
  }

  @Test
  fun limitOfTaxFreeThreshold()
  {
      val taxableIncome = 18_200.0
      assertEquals(0.0, IncomeTax.baseIncomeTax(taxableIncome), 0.0)
  }


  @Test
  fun withinLowIncomeBracket()
  {
      val taxableIncome = 30_000.0
      assertEquals(2_242.0, IncomeTax.baseIncomeTax(taxableIncome), 0.0)
  }

  @Test
  fun limitOfLowIncomeBracket()
  {
      val taxableIncome = 37_000.0
      assertEquals(3_572.0, IncomeTax.baseIncomeTax(taxableIncome), 0.0)
  }

  @Test
  fun justAboveLowIncomeBracket()
  {
      val taxableIncome = 37_011.0
      assertEquals(3_576.0, IncomeTax.baseIncomeTax(taxableIncome), 0.0)
  }

  @Test
  fun withinMiddleIncomeBracket()
  {
      val taxableIncome = 60_000.0
      assertEquals(11_047.0, IncomeTax.baseIncomeTax(taxableIncome), 0.0)
  }

  @Test
  fun limitOfMiddleIncomeBracket()
  {
      val taxableIncome = 90_000.0
      assertEquals(20_797.0, IncomeTax.baseIncomeTax(taxableIncome), 0.0)
  }

  @Test
  fun justAboveMiddleIncomeBracket()
  {
      val taxableIncome = 90_100.0
      assertEquals(20_834.0, IncomeTax.baseIncomeTax(taxableIncome), 0.0)
  }

  @Test
  fun withinHighIncomeBracket()
  {
      val taxableIncome = 150_000.0
      assertEquals(42_997.0, IncomeTax.baseIncomeTax(taxableIncome), 0.0)
  }

  @Test
  fun limitOfHighIncomeBracket()
  {
      val taxableIncome = 180_000.0
      assertEquals(54_097.0, IncomeTax.baseIncomeTax(taxableIncome), 0.0)
  }

  @Test
  fun justAboveHighIncomeBracket()
  {
      val taxableIncome = 180_100.0
      assertEquals(54_142.0, IncomeTax.baseIncomeTax(taxableIncome), 0.0)
  }

  // TODO need to take any crazy rich people taxes into account

  @Test
  fun withinUberHighIncomeBracket()
  {
      val taxableIncome = 250_000.0
      assertEquals(85_597.0, IncomeTax.baseIncomeTax(taxableIncome), 0.0)
  }

  @Test
  fun salaryAtOneMillionDollarsJustToMakeAPoint()
  {
      val taxableIncome = 1_000_000.0
      assertEquals(423_097.0, IncomeTax.baseIncomeTax(taxableIncome), 0.0)
  }


  /** IncomeTaxOffsetTest **/
  @Test
  fun lowIncomeEarnerWithTaxFullyOffset()
  {
    val taxableIncome = 19_000.0
    val incomeTax = 152.0
    assertEquals(152.0, IncomeTax.taxOffsets(taxableIncome, incomeTax), 0.0)
  }

  @Test
  fun lowIncomeEarnerWithTaxFullyOffsetAndNearTheCap()
  {
    val taxableIncome = 21_500.0
    val incomeTax = 627.0
    assertEquals(627.0, IncomeTax.taxOffsets(taxableIncome, incomeTax), 0.0)
  }

  @Test
  fun lowIncomeEarnerMaximumOffset()
  {
    val taxableIncome = 21_594.0
    val incomeTax = 645.0
    assertEquals(645.0, IncomeTax.taxOffsets(taxableIncome, incomeTax), 0.0)
  }

  @Test
  fun lowIncomeEarnerBracketLimit()
  {
    val taxableIncome = 37_000.0
    val incomeTax = 3_572.0
    assertEquals(645.0, IncomeTax.taxOffsets(taxableIncome, incomeTax), 0.0)
  }

  @Test
  fun lowToMiddleIncomeEarnerTaxOffsets()
  {
    val taxableIncome = 37_100.0
    val incomeTax = 3_605.0
    assertEquals(646.5, IncomeTax.taxOffsets(taxableIncome, incomeTax), 0.0)
  }

  @Test
  fun maxLowAndMiddleIncomeEarnerTaxOffsets()
  {
    val taxableIncome = 48_000.0
    val incomeTax = 7_147.0
    assertEquals(810.0, IncomeTax.taxOffsets(taxableIncome, incomeTax), 0.0)
  }

  @Test
  fun maxLowIncomeEarnerTaxOffsets()
  {
    val taxableIncome = 66_667.0
    val incomeTax = 13_214.0
    assertEquals(530.00, IncomeTax.taxOffsets(taxableIncome, incomeTax), 0.0)
  }

  @Test
  fun beginTaperingLowIncomeEarnerTaxOffsets()
  {
    val taxableIncome = 66_667.0
    val incomeTax = 14_297.0
    assertEquals(530.0, IncomeTax.taxOffsets(taxableIncome, incomeTax), 0.0)
  }

  @Test
  fun limitOfMiddleIncomeEarnerTaxOffset()
  {
    val taxableIncome = 90_000.0
    val incomeTax = 20_797.0
    assertEquals(530.0, IncomeTax.taxOffsets(taxableIncome, incomeTax), 0.0)
  }

  @Test
  fun taperingOfLowAndOfMiddleIncomeEarnerTaxOffset()
  {
    val taxableIncome = 100_000.0
    val incomeTax = 24_497.0
    assertEquals(380.0, IncomeTax.taxOffsets(taxableIncome, incomeTax), 0.0)
  }

  @Test
  fun almostFullTaperingOfAllTaxOffsets()
  {
    val taxableIncome = 120_000.0
    val incomeTax = 31_897.0
    assertEquals(80.0, IncomeTax.taxOffsets(taxableIncome, incomeTax), 0.0)
  }

  @Test
  fun boundaryOfZeroTaxOffsets()
  {
    val taxableIncome = 125_333.0
    val incomeTax = 33_870.00
    assertEquals(0.0, IncomeTax.taxOffsets(taxableIncome, incomeTax), 0.0)
  }

  @Test
  fun aboveBoundaryOfZeroTaxOffsets()
  {
    val taxableIncome = 130_000.0
    val incomeTax = 35_597.00
    assertEquals(0.0, IncomeTax.taxOffsets(taxableIncome, incomeTax), 0.0)
  }

  @Test
  fun testTaxableIncome()
  {
    val taxableIncome = IncomeTax.taxableIncome(
      salary = 200_000.0,
      netCapitalGains = 1_000.0,
      superSalarySacrifice = 25_000.0,
      deductions = 15_000.0
    )

    assertEquals(161_000.0, taxableIncome, 0.0)
  }

  @Test
  fun testMedicareIncome()
  {
    val incomeForMedicareLevyPurposes = IncomeTax.medicareIncome(
      taxableIncome = 100_000.0,
      pretaxSuperContribution = 25_000.0
    )
    assertEquals(125_000.0, incomeForMedicareLevyPurposes, 0.0)
  }

  @Test
  fun testMedicare_WithIncomeBelowSurchargeThreshold()
  {
    val medicareLevy = IncomeTax.medicare(
      income = 89_000.0,
      hasHealthInsurance = false,
      warnings = mutableListOf<ValidationWarning>()
    )
    assertEquals(0.02 * 89_000.0, medicareLevy, 0.0)
  }

  @Test
  fun testMedicare_WithIncomeAboveLowestSurchargeThresholdAndNoInsurance()
  {
    val medicareLevy = IncomeTax.medicare(
      income = 91_000.0,
      hasHealthInsurance = false,
      warnings = mutableListOf<ValidationWarning>()
    )
    assertEquals(0.02 * 91_000.0 + 0.01 * 91_000.0, medicareLevy, 0.0)
  }

  @Test
  fun testLevySurchargeRate_NoInsurance()
  {
    val expected: Map<Double, Double> = mapOf(
      10_000.0 to 0.0000,
      50_000.0 to 0.0000,
      90_000.0 to 0.0000,
      90_001.0 to 0.0100,
      105_000.0 to 0.0100,
      105_001.0 to 0.0125,
      140_000.0 to 0.0125,
      140_001.0 to 0.0350,
      200_000.0 to 0.0350,
      1_000_000.0 to 0.0350
    )

    val outputs = expected.keys.map {
        income -> income to IncomeTax.levySurchargeRate(income, false)
    }.toMap()

    assertEquals(expected, outputs)
  }

  @Test
  fun testLevySurchargeRate_WithInsurance()
  {
    val expected: Map<Double, Double> = mapOf(
      10_000.0 to 0.0,
      50_000.0 to 0.0,
      90_000.0 to 0.0,
      90_001.0 to 0.0,
      105_000.0 to 0.0,
      105_001.0 to 0.0,
      140_000.0 to 0.0,
      140_001.0 to 0.0,
      200_000.0 to 0.0,
      1_000_000.0 to 0.0
    )

    val outputs = expected.keys.map {
        income -> income to IncomeTax.levySurchargeRate(income, true)
    }.toMap()

    assertEquals(expected, outputs)
  }

  /** E2EIncomeTaxTest **/
  @Test
  fun testIncomeTax_BaseIncomeTaxWithHealthInsurance()
  {
    val incomeTaxPayable = IncomeTax.incomeTax(
      taxableIncome = 100_000.0,
      compulsorySuper = 10_000.0,
      superSalarySacrifice = 0.0,
      hasHealthInsurance = true,
      warnings = mutableListOf<ValidationWarning>()
    )

    val expected = IncomeTaxPayable(
      incomeTax = 24_497.00,
      medicare = 2_200.0,
      taxOffset = 380.0
    )

    assertEquals(expected.incomeTax, incomeTaxPayable.incomeTax, 0.0)
    assertEquals(expected.medicare, incomeTaxPayable.medicare, 0.0)
    assertEquals(expected.taxOffset, incomeTaxPayable.taxOffset, 0.0)

    assertEquals(expected.total(), incomeTaxPayable.total(), 0.0)
  }

  @Test
  fun testIncomeTax_BaseIncomeTaxWithoutHealthInsurance()
  {
    val incomeTaxPayable = IncomeTax.incomeTax(
      taxableIncome = 100_000.0,
      compulsorySuper = 10_000.0,
      superSalarySacrifice = 0.0,
      hasHealthInsurance = false,
      warnings = mutableListOf<ValidationWarning>()
    )

    val expected = IncomeTaxPayable(
      incomeTax = 24_497.00,
      medicare = 0.02 * 110_000.0 + 0.0125 * 110_000.0,
      taxOffset = 380.0
    )

    assertEquals(expected.incomeTax, incomeTaxPayable.incomeTax, 0.0)
    assertEquals(expected.medicare, incomeTaxPayable.medicare, 0.0)
    assertEquals(expected.taxOffset, incomeTaxPayable.taxOffset, 0.0)

    assertEquals(expected.total(), incomeTaxPayable.total(), 0.0)
  }

  @Test
  fun testIncomeTax_WithSalarySacrifice()
  {
    val incomeTaxPayable = IncomeTax.incomeTax(
      taxableIncome = 90_000.0,
      compulsorySuper = 10_000.0,
      superSalarySacrifice = 10_000.0,
      hasHealthInsurance = true,
      warnings = mutableListOf<ValidationWarning>()
    )

    val expected = IncomeTaxPayable(
      incomeTax = 20_797.0,
      medicare = 0.02 * 110_000.0,
      taxOffset = 530.0
    )

    assertEquals(expected.incomeTax, incomeTaxPayable.incomeTax, 0.0)
    assertEquals(expected.medicare, incomeTaxPayable.medicare, 0.0)
    assertEquals(expected.taxOffset, incomeTaxPayable.taxOffset, 0.0)

    assertEquals(expected.total(), incomeTaxPayable.total(), 0.0)
  }
}
