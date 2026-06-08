package inspection

import checkRule
import checkRuleSortedDescending
import net.jqwik.api.ForAll
import net.jqwik.api.Property
import net.jqwik.api.constraints.NumericChars
import net.jqwik.api.constraints.StringLength
import runTest
import kotlin.math.max


class DigitCountTest {
    @Property
    fun `return string length without leading zeros`(
        @ForAll @NumericChars @StringLength(min = 0, max = 100) string: String
    ): Boolean = runTest(
        string, string.trimStart('0').length
    ) { it.digitCount }

    @Property
    fun `check length preservation or incrementation under addition`(
        @ForAll @NumericChars @StringLength(min = 0, max = 100) string1: String,
        @ForAll @NumericChars @StringLength(min = 0, max = 100) string2: String,
    ): Boolean = checkRule(string1, string2) { number1, number2 ->
        val maxNumbersLength = max(number1.digitCount, number2.digitCount)
        val result = number1 + number2
        val resultLength = result.digitCount

        val lengthRemained = resultLength == maxNumbersLength
        val lengthIncremented = resultLength == maxNumbersLength + 1
        val firstDigitIsOne = result.toString().startsWith('1')

        lengthRemained || lengthIncremented && firstDigitIsOne
    }

    @Property
    fun `check length upper bound under subtraction`(
        @ForAll @NumericChars @StringLength(min = 0, max = 100) string1: String,
        @ForAll @NumericChars @StringLength(min = 0, max = 100) string2: String,
    ): Boolean = checkRuleSortedDescending(string1, string2) { number1, number2 ->
        number1.digitCount >= (number1 - number2).digitCount
    }

    @Property
    fun `check length lower bound under subtraction`(
        @ForAll @NumericChars @StringLength(min = 0, max = 100) string1: String,
        @ForAll @NumericChars @StringLength(min = 0, max = 100) string2: String,
    ): Boolean = checkRuleSortedDescending(string1, string2) { number1, number2 ->
        (number1 - number2).digitCount >= number1.digitCount - number2.digitCount
    }
}
