import net.jqwik.api.Assume
import net.jqwik.api.ForAll
import net.jqwik.api.Property
import net.jqwik.api.constraints.NumericChars
import net.jqwik.api.constraints.StringLength


class EqualsTest {
    @Property
    fun `check correct work on self`(
        @ForAll @NumericChars @StringLength(min = 0, max = 100) string1: String
    ): Boolean = checkRule(string1, string1) { number1, number2 ->
        number1 == number2
    }

    @Property
    fun `check correct work on other VastNatural`(
        @ForAll @NumericChars @StringLength(min = 0, max = 100) string1: String,
        @ForAll @NumericChars @StringLength(min = 0, max = 100) string2: String,
    ): Boolean {
        val stringsEquality = compareValuesBy(string1, string2)
        { it.trimStart('0') } == 0
        return checkRule(string1, string2) { number1, number2 ->
            val numbersEquality = number1 == number2
            stringsEquality == numbersEquality
        }
    }

    @Property
    fun `check correct work on non-VastNatural`(
        @ForAll @NumericChars @StringLength(min = 0, max = 100) string: String,
        @ForAll any: Any?
    ): Boolean = BytePerDigit(string) != any

    @Property
    fun `check symmetry`(
        @ForAll @NumericChars @StringLength(min = 0, max = 100) string1: String,
        @ForAll @NumericChars @StringLength(min = 0, max = 100) string2: String,
    ): Boolean = checkRule(string1, string2) { number1, number2 ->
        val directEquality = number1 == number2
        val invertedEquality = number2 == number1
        directEquality == invertedEquality
    }

    @Property
    fun `check transitivity`(
        @ForAll @NumericChars @StringLength(min = 0, max = 100) string1: String,
        @ForAll @NumericChars @StringLength(min = 0, max = 100) string2: String,
    ): Boolean {
        val strings = listOf(string1, string1, string2).shuffled()

        val equation12 = strings[0] == strings[1]
        val equation23 = strings[1] == strings[2]

        Assume.that(equation12 || equation23)

        return checkRule(string1, string2) { number1, number3 ->
            val equation13 = number1 == number3
            equation13 == equation12 ||
                    equation13 == equation23
        }
    }

    @Property
    fun `check reflexivity`(
        @ForAll @NumericChars @StringLength(min = 0, max = 100) string: String
    ): Boolean = BytePerDigit(string) == BytePerDigit(string)

    @Property
    fun `check compareTo-equals consistency`(
        @ForAll @NumericChars @StringLength(min = 0, max = 100) string1: String,
        @ForAll @NumericChars @StringLength(min = 0, max = 100) string2: String,
    ): Boolean = checkRule(string1, string2) { number1, number2 ->
        val comparisonResult = number1 compareTo number2
        val equalityResult = number1 == number2
        (comparisonResult == 0) == equalityResult
    }
}
