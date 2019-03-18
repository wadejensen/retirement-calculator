package com.wadejensen.retirement.tax

import com.wadejensen.retirement.IncomeTax
import com.wadejensen.retirement.tax.data.IncomeTaxPayable
import kotlin.test.Test
import kotlin.test.assertEquals

class IncomeTaxTest {
  /** BaseIncomeTaxTest **/
  @Test
  fun negativeIncome()
  {
    val taxableIncome = -10_000.0
    val incomeTax = IncomeTax.baseIncomeTax(taxableIncome)
    assertEquals(expected = 0.0, actual = incomeTax)
  }

  @Test
  fun zeroIncome()
  {
      val taxableIncome = 0.0
      assertEquals(expected = 0.0, actual = IncomeTax.baseIncomeTax(taxableIncome))
  }

  @Test
  fun withinTaxFreeThreshold()
  {
      val taxableIncome = 10_000.0
      assertEquals(expected = 0.0, actual = IncomeTax.baseIncomeTax(taxableIncome))
  }

  @Test
  fun limitOfTaxFreeThreshold()
  {
      val taxableIncome = 18_200.0
      assertEquals(expected = 0.0, actual = IncomeTax.baseIncomeTax(taxableIncome))
  }


  @Test
  fun withinLowIncomeBracket()
  {
      val taxableIncome = 30_000.0
      assertEquals(expected = 2_242.0, actual = IncomeTax.baseIncomeTax(taxableIncome))
  }

  @Test
  fun limitOfLowIncomeBracket()
  {
      val taxableIncome = 37_000.0
      assertEquals(expected = 3_572.0, actual = IncomeTax.baseIncomeTax(taxableIncome))
  }

  @Test
  fun justAboveLowIncomeBracket()
  {
      val taxableIncome = 37_011.0
      assertEquals(expected = 3_576.0, actual = IncomeTax.baseIncomeTax(taxableIncome))
  }

  @Test
  fun withinMiddleIncomeBracket()
  {
      val taxableIncome = 60_000.0
      assertEquals(expected = 11_047.0, actual = IncomeTax.baseIncomeTax(taxableIncome))
  }

  @Test
  fun limitOfMiddleIncomeBracket()
  {
      val taxableIncome = 90_000.0
      assertEquals(expected = 20_797.0, actual = IncomeTax.baseIncomeTax(taxableIncome))
  }

  @Test
  fun justAboveMiddleIncomeBracket()
  {
      val taxableIncome = 90_100.0
      assertEquals(expected = 20_834.0, actual = IncomeTax.baseIncomeTax(taxableIncome))
  }

  @Test
  fun withinHighIncomeBracket()
  {
      val taxableIncome = 150_000.0
      assertEquals(expected = 42_997.0, actual = IncomeTax.baseIncomeTax(taxableIncome))
  }

  @Test
  fun limitOfHighIncomeBracket()
  {
      val taxableIncome = 180_000.0
      assertEquals(expected = 54_097.0, actual = IncomeTax.baseIncomeTax(taxableIncome))
  }

  @Test
  fun justAboveHighIncomeBracket()
  {
      val taxableIncome = 180_100.0
      assertEquals(expected = 54_142.0, actual = IncomeTax.baseIncomeTax(taxableIncome))
  }

  // TODO need to take any crazy rich people taxes into account

  @Test
  fun withinUberHighIncomeBracket()
  {
      val taxableIncome = 250_000.0
      assertEquals(expected = 85_597.0, actual = IncomeTax.baseIncomeTax(taxableIncome))
  }

  @Test
  fun salaryAtOneMillionDollarsJustToMakeAPoint()
  {
      val taxableIncome = 1_000_000.0
      assertEquals(expected = 423_097.0, actual = IncomeTax.baseIncomeTax(taxableIncome))
  }


  /** IncomeTaxOffsetTest **/
  @Test
  fun lowIncomeEarnerWithTaxFullyOffset()
  {
    val taxableIncome = 19_000.0
    val incomeTax = 152.0
    assertEquals(expected = 152.0, actual = IncomeTax.taxOffsets(taxableIncome, incomeTax))
  }

  @Test
  fun lowIncomeEarnerWithTaxFullyOffsetAndNearTheCap()
  {
    val taxableIncome = 21_500.0
    val incomeTax = 627.0
    assertEquals(expected = 627.0, actual = IncomeTax.taxOffsets(taxableIncome, incomeTax))
  }

  @Test
  fun lowIncomeEarnerMaximumOffset()
  {
    val taxableIncome = 21_594.0
    val incomeTax = 645.0
    assertEquals(expected = 645.0, actual = IncomeTax.taxOffsets(taxableIncome, incomeTax))
  }

  @Test
  fun lowIncomeEarnerBracketLimit()
  {
    val taxableIncome = 37_000.0
    val incomeTax = 3_572.0
    assertEquals(expected = 645.0, actual = IncomeTax.taxOffsets(taxableIncome, incomeTax))
  }

  @Test
  fun lowToMiddleIncomeEarnerTaxOffsets()
  {
    val taxableIncome = 37_100.0
    val incomeTax = 3_605.0
    assertEquals(expected = 646.5, actual = IncomeTax.taxOffsets(taxableIncome, incomeTax))
  }

  @Test
  fun maxLowAndMiddleIncomeEarnerTaxOffsets()
  {
    val taxableIncome = 48_000.0
    val incomeTax = 7_147.0
    assertEquals(expected = 810.0, actual = IncomeTax.taxOffsets(taxableIncome, incomeTax))
  }

  @Test
  fun maxLowIncomeEarnerTaxOffsets()
  {
    val taxableIncome = 66_667.0
    val incomeTax = 13_214.0
    assertEquals(expected = 530.00, actual = IncomeTax.taxOffsets(taxableIncome, incomeTax))
  }

  @Test
  fun beginTaperingLowIncomeEarnerTaxOffsets()
  {
    val taxableIncome = 66_667.0
    val incomeTax = 14_297.0
    assertEquals(expected = 530.0, actual = IncomeTax.taxOffsets(taxableIncome, incomeTax))
  }

  @Test
  fun limitOfMiddleIncomeEarnerTaxOffset()
  {
    val taxableIncome = 90_000.0
    val incomeTax = 20_797.0
    assertEquals(expected = 530.0, actual = IncomeTax.taxOffsets(taxableIncome, incomeTax))
  }

  @Test
  fun taperingOfLowAndOfMiddleIncomeEarnerTaxOffset()
  {
    val taxableIncome = 100_000.0
    val incomeTax = 24_497.0
    assertEquals(expected = 380.0, actual = IncomeTax.taxOffsets(taxableIncome, incomeTax))
  }

  @Test
  fun almostFullTaperingOfAllTaxOffsets()
  {
    val taxableIncome = 120_000.0
    val incomeTax = 31_897.0
    assertEquals(expected = 80.0, actual = IncomeTax.taxOffsets(taxableIncome, incomeTax))
  }

  @Test
  fun boundaryOfZeroTaxOffsets()
  {
    val taxableIncome = 125_333.0
    val incomeTax = 33_870.00
    assertEquals(expected = 0.0, actual = IncomeTax.taxOffsets(taxableIncome, incomeTax))
  }

  @Test
  fun aboveBoundaryOfZeroTaxOffsets()
  {
    val taxableIncome = 130_000.0
    val incomeTax = 35_597.00
    assertEquals(expected = 0.0, actual = IncomeTax.taxOffsets(taxableIncome, incomeTax))
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

    assertEquals(161_000.0, taxableIncome)
  }

  @Test
  fun testMedicareIncome()
  {
    val incomeForMedicareLevyPurposes = IncomeTax.medicareIncome(
      taxableIncome = 100_000.0,
      pretaxSuperContribution = 25_000.0
    )
    assertEquals(125_000.0, incomeForMedicareLevyPurposes)
  }

  @Test
  fun testMedicare_WithIncomeBelowSurchargeThreshold()
  {
    val medicareLevy = IncomeTax.medicare(
      income = 89_000.0, hasHealthInsurance = false
    )
    assertEquals(0.02 * 89_000.0, medicareLevy)
  }

  @Test
  fun testMedicare_WithIncomeAboveLowestSurchargeThresholdAndNoInsurance()
  {
    val medicareLevy = IncomeTax.medicare(
      income = 91_000.0, hasHealthInsurance = false
    )
    assertEquals(0.02 * 91_000.0 + 0.01 * 91_000.0, medicareLevy)
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
      hasHealthInsurance = true
    )

    val expected = IncomeTaxPayable(
      incomeTax = 24_497.00,
      medicare = 2_200.0,
      taxOffset = 380.0
    )

    assertEquals(expected.incomeTax, incomeTaxPayable.incomeTax)
    assertEquals(expected.medicare, incomeTaxPayable.medicare)
    assertEquals(expected.taxOffset, incomeTaxPayable.taxOffset)

    assertEquals(expected.total(), incomeTaxPayable.total())
  }

  @Test
  fun testIncomeTax_BaseIncomeTaxWithoutHealthInsurance()
  {
    val incomeTaxPayable = IncomeTax.incomeTax(
      taxableIncome = 100_000.0,
      compulsorySuper = 10_000.0,
      superSalarySacrifice = 0.0,
      hasHealthInsurance = false
    )

    val expected = IncomeTaxPayable(
      incomeTax = 24_497.00,
      medicare = 0.02 * 110_000.0 + 0.0125 * 110_000.0,
      taxOffset = 380.0
    )

    assertEquals(expected.incomeTax, incomeTaxPayable.incomeTax)
    assertEquals(expected.medicare, incomeTaxPayable.medicare)
    assertEquals(expected.taxOffset, incomeTaxPayable.taxOffset)

    assertEquals(expected.total(), incomeTaxPayable.total())
  }

  @Test
  fun testIncomeTax_WithSalarySacrifice()
  {
    val incomeTaxPayable = IncomeTax.incomeTax(
      taxableIncome = 90_000.0,
      compulsorySuper = 10_000.0,
      superSalarySacrifice = 10_000.0,
      hasHealthInsurance = true
    )

    val expected = IncomeTaxPayable(
      incomeTax = 20_797.0,
      medicare = 0.02 * 110_000.0,
      taxOffset = 530.0
    )

    assertEquals(expected.incomeTax, incomeTaxPayable.incomeTax)
    assertEquals(expected.medicare, incomeTaxPayable.medicare)
    assertEquals(expected.taxOffset, incomeTaxPayable.taxOffset)

    assertEquals(expected.total(), incomeTaxPayable.total())
  }
}
