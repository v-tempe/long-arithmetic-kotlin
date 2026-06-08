package arithmetic

import BytePerDigit
import checkRule
import checkRuleSortedDescending
import net.jqwik.api.Assume
import net.jqwik.api.ForAll
import net.jqwik.api.Property
import net.jqwik.api.constraints.NumericChars
import net.jqwik.api.constraints.StringLength
import kotlin.test.assertFailsWith


class SubtractionTest {
    @Property
    fun `check correct work`(
        @ForAll @NumericChars @StringLength(min = 1, max = 100) string1: String,
        @ForAll @NumericChars @StringLength(min = 1, max = 100) string2: String,
    ): Boolean {
        val expectedOperand1 = string1.toBigInteger()
        val expectedOperand2 = string2.toBigInteger()
        Assume.that(expectedOperand1 >= expectedOperand2)

        val expectedResult = expectedOperand1 - expectedOperand2

        return checkRule(string1, string2) { number1, number2 ->
            val expected = BytePerDigit(expectedResult.toString())
            val actual = number1 - number2

            expected == actual
        }
    }

    @Property
    fun `throw an exception when minuend less than subtrahend`(
        @ForAll @NumericChars @StringLength(min = 0, max = 100) string1: String,
        @ForAll @NumericChars @StringLength(min = 0, max = 100) string2: String,
    ): Unit {
        val number1 = BytePerDigit(string1)
        val number2 = BytePerDigit(string2)

        Assume.that(number1 < number2)

        assertFailsWith<IllegalArgumentException> {
            number1 - number2
        }
    }

    @Property
    fun `check monotony`(
        @ForAll @NumericChars @StringLength(min = 0, max = 100) string1: String,
        @ForAll @NumericChars @StringLength(min = 0, max = 100) string2: String,
    ): Boolean = checkRuleSortedDescending(string1, string2) { number1, number2 ->
        number1 >= number1 - number2
    }

    @Property
    fun `check neutral element subtraction`(
        @ForAll @NumericChars @StringLength(min = 0, max = 100) string: String
    ): Boolean {
        val number = BytePerDigit(string)
        val zero = BytePerDigit()

        return number - zero == number
    }

    @Property
    fun `check self subtraction`(
        @ForAll @NumericChars @StringLength(min = 0, max = 100) string: String
    ): Boolean {
        val number = BytePerDigit(string)
        val zero = BytePerDigit()

        return number - number == zero
    }

    @Property
    fun `check consistency with addition through decrease`(
        @ForAll @NumericChars @StringLength(min = 0, max = 100) string1: String,
        @ForAll @NumericChars @StringLength(min = 0, max = 100) string2: String,
    ): Boolean = checkRuleSortedDescending(string1, string2) { number1, number2 ->
        (number1 - number2) + number2 == number1
    }

    @Property
    fun `check consistency with addition through increase`(
        @ForAll @NumericChars @StringLength(min = 0, max = 100) string1: String,
        @ForAll @NumericChars @StringLength(min = 0, max = 100) string2: String,
    ): Boolean = checkRuleSortedDescending(string1, string2) { number1, number2 ->
        (number1 + number2) - number2 == number1
    }
}
