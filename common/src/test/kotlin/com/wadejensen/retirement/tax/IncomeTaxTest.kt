package com.wadejensen.retirement.tax

import kotlin.test.Test
import kotlin.test.assertEquals

class IncomeTaxTest {
    @Test
    fun negativeIncome()
    {
        val taxableIncome = -10_000.0
        assertEquals(expected = 0.0, actual = IncomeTax.incomeTax(taxableIncome))
    }

    @Test
    fun zeroIncome()
    {
        val taxableIncome = 0.0
        assertEquals(expected = 0.0, actual = IncomeTax.incomeTax(taxableIncome))
    }

    @Test
    fun withinTaxFreeThreshold()
    {
        val taxableIncome = 10_000.0
        assertEquals(expected = 0.0, actual = IncomeTax.incomeTax(taxableIncome))
    }

    @Test
    fun limitOfTaxFreeThreshold()
    {
        val taxableIncome = 18_200.0
        assertEquals(expected = 0.0, actual = IncomeTax.incomeTax(taxableIncome))
    }


    @Test
    fun withinLowIncomeBracket()
    {
        val taxableIncome = 30_000.0
        assertEquals(expected = 2_242.0, actual = IncomeTax.incomeTax(taxableIncome))
    }

    @Test
    fun limitOfLowIncomeBracket()
    {
        val taxableIncome = 37_000.0
        assertEquals(expected = 3_572.0, actual = IncomeTax.incomeTax(taxableIncome))
    }

    @Test
    fun justAboveLowIncomeBracket()
    {
        val taxableIncome = 37_011.0
        assertEquals(expected = 3_576.0, actual = IncomeTax.incomeTax(taxableIncome))
    }

    @Test
    fun withinMiddleIncomeBracket()
    {
        val taxableIncome = 60_000.0
        assertEquals(expected = 11_047.0, actual = IncomeTax.incomeTax(taxableIncome))
    }

    @Test
    fun limitOfMiddleIncomeBracket()
    {
        val taxableIncome = 90_000.0
        assertEquals(expected = 20_797.0, actual = IncomeTax.incomeTax(taxableIncome))
    }

    @Test
    fun justAboveMiddleIncomeBracket()
    {
        val taxableIncome = 90_100.0
        assertEquals(expected = 20_834.0, actual = IncomeTax.incomeTax(taxableIncome))
    }

    @Test
    fun withinHighIncomeBracket()
    {
        val taxableIncome = 150_000.0
        assertEquals(expected = 42_997.0, actual = IncomeTax.incomeTax(taxableIncome))
    }

    @Test
    fun limitOfHighIncomeBracket()
    {
        val taxableIncome = 180_000.0
        assertEquals(expected = 54_097.0, actual = IncomeTax.incomeTax(taxableIncome))
    }

    @Test
    fun justAboveHighIncomeBracket()
    {
        val taxableIncome = 180_100.0
        assertEquals(expected = 54_142.0, actual = IncomeTax.incomeTax(taxableIncome))
    }

    // TODO need to take any crazy rich people taxes into account

    @Test
    fun withinUberHighIncomeBracket()
    {
        val taxableIncome = 250_000.0
        assertEquals(expected = 85_597.0, actual = IncomeTax.incomeTax(taxableIncome))
    }

    @Test
    fun salaryAtOneMillionDollarsJustToMakeAPoint()
    {
        val taxableIncome = 1_000_000.0
        assertEquals(expected = 423_097.0, actual = IncomeTax.incomeTax(taxableIncome))
    }
}

class IncomeTaxOffsetTest {
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
}
