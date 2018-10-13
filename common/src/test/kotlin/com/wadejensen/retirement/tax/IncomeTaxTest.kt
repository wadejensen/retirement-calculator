package com.wadejensen.retirement.tax

import kotlin.test.Test
import kotlin.test.assertEquals

class IncomeTaxTest {
    @Test
    fun negative_income() {
        val taxableIncome = -10_000.0
        assertEquals(expected = 0.0, actual = incomeTax(taxableIncome))
    }

    @Test
    fun zero_income() {
        val taxableIncome = 0.0
        assertEquals(expected = 0.0, actual = incomeTax(taxableIncome))
    }

    @Test
    fun within_tax_free_threshold() {
        val taxableIncome = 10_000.0
        assertEquals(expected = 0.0, actual = incomeTax(taxableIncome))
    }

    @Test
    fun limit_of_tax_free_threshold() {
        val taxableIncome = 18_200.0
        assertEquals(expected = 0.0, actual = incomeTax(taxableIncome))
    }


    @Test
    fun within_low_income_bracket() {
        val taxableIncome = 30_000.0
        assertEquals(expected = 2_242.0, actual = incomeTax(taxableIncome))
    }

    @Test
    fun limit_of_low_income_bracket() {
        val taxableIncome = 37_000.0
        assertEquals(expected = 3_572.0, actual = incomeTax(taxableIncome))
    }

    @Test
    fun just_above_low_income_bracket() {
        val taxableIncome = 37_011.0
        assertEquals(expected = 3_576.0, actual = incomeTax(taxableIncome))
    }

    @Test
    fun within_middle_income_bracket() {
        val taxableIncome = 60_000.0
        assertEquals(expected = 11_047.0, actual = incomeTax(taxableIncome))
    }

    @Test
    fun limit_of_middle_income_bracket() {
        val taxableIncome = 90_000.0
        assertEquals(expected = 20_797.0, actual = incomeTax(taxableIncome))
    }

    @Test
    fun just_above_middle_income_bracket() {
        val taxableIncome = 90_100.0
        assertEquals(expected = 20_834.0, actual = incomeTax(taxableIncome))
    }

    @Test
    fun within_high_income_bracket() {
        val taxableIncome = 150_000.0
        val financialYear = 2018
        assertEquals(expected = 42_997.0, actual = incomeTax(taxableIncome))
    }

    @Test
    fun limit_of_high_income_bracket() {
        val taxableIncome = 180_000.0
        assertEquals(expected = 54_097.0, actual = incomeTax(taxableIncome))
    }

    @Test
    fun just_above_high_income_bracket() {
        val taxableIncome = 180_100.0
        assertEquals(expected = 54_142.0, actual = incomeTax(taxableIncome))
    }

    // TODO need to take any crazy rich people taxes into account

    @Test
    fun within_uber_high_income_bracket() {
        val taxableIncome = 250_000.0
        assertEquals(expected = 85_597.0, actual = incomeTax(taxableIncome))
    }

    @Test
    fun salary_at_one_million_dollars_just_to_make_a_point() {
        val taxableIncome = 1_000_000.0
        assertEquals(expected = 423_097.0, actual = incomeTax(taxableIncome))
    }
}

class IncomeTaxOffsetTest {
    @Test
    fun low_income_earner_with_tax_fully_offset() {
        val taxableIncome = 19_000.0
        val incomeTax = 152.0
        assertEquals(expected = 152.0, actual = incomeTaxOffsets(taxableIncome, incomeTax))
    }

    @Test
    fun low_income_earner_with_tax_fully_offset_and_near_the_cap() {
        val taxableIncome = 21_500.0
        val incomeTax = 627.0
        assertEquals(expected = 627.0, actual = incomeTaxOffsets(taxableIncome, incomeTax))
    }

    @Test
    fun low_income_earner_maximum_offset() {
        val taxableIncome = 21_594.0
        val incomeTax = 645.0
        assertEquals(expected = 645.0, actual = incomeTaxOffsets(taxableIncome, incomeTax))
    }

    @Test
    fun low_income_earner_bracket_limit() {
        val taxableIncome = 37_000.0
        val incomeTax = 3_572.0
        assertEquals(expected = 645.0, actual = incomeTaxOffsets(taxableIncome, incomeTax))
    }

    @Test
    fun low_to_middle_income_earner_tax_offsets() {
        val taxableIncome = 37_100.0
        val incomeTax = 3_605.0
        assertEquals(expected = 646.5, actual = incomeTaxOffsets(taxableIncome, incomeTax))
    }

    @Test
    fun max_low_and_middle_income_earner_tax_offsets() {
        val taxableIncome = 48_000.0
        val incomeTax = 7_147.0
        assertEquals(expected = 810.0, actual = incomeTaxOffsets(taxableIncome, incomeTax))
    }

    @Test
        fun max_low_income_earner_tax_offsets() {
        val taxableIncome = 66_667.0
        val incomeTax = 13_214.0
        assertEquals(expected = 530.00, actual = incomeTaxOffsets(taxableIncome, incomeTax))
    }

    @Test
    fun begin_tapering_low_income_earner_tax_offsets() {
        val taxableIncome = 66_667.0
        val incomeTax = 14_297.0
        assertEquals(expected = 530.0, actual = incomeTaxOffsets(taxableIncome, incomeTax))
    }

    @Test
    fun limit_of_middle_income_earner_tax_offset() {
        val taxableIncome = 90_000.0
        val incomeTax = 20_797.0
        assertEquals(expected = 530.0, actual = incomeTaxOffsets(taxableIncome, incomeTax))
    }

    @Test
    fun tapering_of_low_and_of_middle_income_earner_tax_offset() {
        val taxableIncome = 100_000.0
        val incomeTax = 24_497.0
        assertEquals(expected = 380.0, actual = incomeTaxOffsets(taxableIncome, incomeTax))
    }

    @Test
    fun almost_full_tapering_of_all_tax_offsets() {
        val taxableIncome = 120_000.0
        val incomeTax = 31_897.0
        assertEquals(expected = 80.0, actual = incomeTaxOffsets(taxableIncome, incomeTax))
    }

    @Test
    fun boundary_of_zero_tax_offsets() {
        val taxableIncome = 125_333.0
        val incomeTax = 33_870.00
        assertEquals(expected = 0.0, actual = incomeTaxOffsets(taxableIncome, incomeTax))
    }

    @Test
    fun above_boundary_of_zero_tax_offsets() {
        val taxableIncome = 130_000.0
        val incomeTax = 35_597.00
        assertEquals(expected = 0.0, actual = incomeTaxOffsets(taxableIncome, incomeTax))
    }
}
