import net.jqwik.api.Assume
import net.jqwik.api.ForAll
import net.jqwik.api.Property
import net.jqwik.api.constraints.Chars
import net.jqwik.api.constraints.NumericChars
import net.jqwik.api.constraints.StringLength


class ToStringTest {
    @Property(tries = 100)
    fun `return single zero on zero value`(
        @ForAll @Chars('0') @StringLength(min = 0, max = 20) string: String
    ): Boolean {
        Assume.that( string.isAllZeros())

        return runTest(string, "0") { it.toString() }
    }

    @Property
    fun `trim leading zeros on non-zero value`(
        @ForAll @NumericChars @StringLength(min = 1, max = 100) string: String
    ): Boolean {
        Assume.that( ! string.isAllZeros())

        return runTest(
            string, string.trimStart('0')
        ) { it.toString() }
    }

    @Property
    fun `trim leading zeros after addition`(
        @ForAll @NumericChars @StringLength(min = 1, max = 100) string1: String,
        @ForAll @NumericChars @StringLength(min = 1, max = 100) string2: String,
    ): Boolean = checkRule(string1, string2) { number1, number2 ->
        val bothZeros = string1.isAllZeros() && string2.isAllZeros()
        Assume.that( ! bothZeros)

        val result = number1 + number2
        val resultStartsWithZero = result.toString().startsWith('0')
        ! resultStartsWithZero
    }

    @Property
    fun `trim leading zeros after subtraction`(
        @ForAll @NumericChars @StringLength(min = 1, max = 100) string1: String,
        @ForAll @NumericChars @StringLength(min = 1, max = 100) string2: String,
    ): Boolean = checkRuleSortedDescending(string1, string2) { number1, number2 ->
        Assume.that(number1 != number2)

        val result = number1 - number2
        val resultStartsWithZero = result.toString().startsWith('0')
        ! resultStartsWithZero
    }
}
