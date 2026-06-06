import net.jqwik.api.Assume
import net.jqwik.api.ForAll
import net.jqwik.api.Property
import net.jqwik.api.constraints.NumericChars
import net.jqwik.api.constraints.StringLength
import kotlin.math.sign


private infix fun String.compareAsNumbersWith(other: String): Int =
    compareValuesBy(
        this.trimStart('0'),
        other.trimStart('0'),
        { it.length }, { it }
    )


private infix fun Int.hasSameSignTo(other: Int): Boolean =
    this.sign == other.sign


private infix fun Int.hasOppositeSignTo(other: Int): Boolean =
    this.sign * other.sign == -1


class ComparisonTest {
    @Property
    fun `check correct work`(
        @ForAll @NumericChars @StringLength(min = 0, max = 100) string1: String,
        @ForAll @NumericChars @StringLength(min = 0, max = 100) string2: String,
    ): Boolean = checkRule(string1, string2) { number1, number2 ->
        number1 compareTo number2 == string1 compareAsNumbersWith string2
    }

    @Property
    fun `check anti-symmetry`(
        @ForAll @NumericChars @StringLength(min = 0, max = 100) string1: String,
        @ForAll @NumericChars @StringLength(min = 0, max = 100) string2: String,
    ): Boolean = checkRule(
        string1, string2
    ) { left, right ->
        val directResult = left.compareTo(right)
        val invertedResult = right.compareTo(left)

        directResult.sign == -invertedResult.sign
    }

    @Property
    fun `check transitivity`(
        @ForAll @NumericChars @StringLength(min = 0, max = 100) string1: String,
        @ForAll @NumericChars @StringLength(min = 0, max = 100) string2: String,
        @ForAll @NumericChars @StringLength(min = 0, max = 100) string3: String,
    ): Boolean {
        val comparisonResult12 = string1 compareAsNumbersWith string2
        val comparisonResult23 = string2 compareAsNumbersWith string3
        Assume.that( ! (comparisonResult12 hasOppositeSignTo comparisonResult23) )

        return checkRule(
            string1, string3
        ) { number1, number3 ->
            val comparisonResult13 = number1 compareTo number3
            comparisonResult13 hasSameSignTo comparisonResult12 ||
                    comparisonResult13 hasSameSignTo comparisonResult23
        }
    }

    @Property(tries = 100)
    fun `check reflexivity`(
        @ForAll @NumericChars @StringLength(min = 0, max = 100) string: String
    ): Boolean = BytePerDigit(string) compareTo BytePerDigit(string) == 0

    @Property
    fun `ensure that any non-zero value is greater than zero`(
        @ForAll @NumericChars @StringLength(min = 1, max = 100) string: String
    ): Boolean {
        Assume.that(!string.isAllZeros())
        return checkRule(string, "0")
        { nonZero, zero -> nonZero > zero }
    }

    @Property
    fun `check integration with TreeSet`(
        @ForAll @NumericChars @StringLength(min = 0, max = 100) string: String
    ): Boolean = checkRule(string, string) { number1, number2 ->
        assert(number1 == number2)
        sortedSetOf(number1, number2).size == 1
    }

    @Property
    fun `check preservation of comparison under addition the same number`(
        @ForAll @NumericChars @StringLength(min = 0, max = 100) string1: String,
        @ForAll @NumericChars @StringLength(min = 0, max = 100) string2: String,
        @ForAll @NumericChars @StringLength(min = 0, max = 100) string3: String,
    ): Boolean = checkRule(string1, string2, string3) { numbers ->
        val (number1, number2, number3) = numbers

        val directComparison = number1 compareTo number2
        val comparisonUnderAddition =
            (number1 + number3) compareTo (number2 + number3)

        directComparison hasSameSignTo comparisonUnderAddition
    }
}
