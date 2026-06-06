import net.jqwik.api.ForAll
import net.jqwik.api.Property
import net.jqwik.api.constraints.NumericChars
import net.jqwik.api.constraints.StringLength


class AdditionTest {
    @Property
    fun `check correct work`(
        @ForAll @NumericChars @StringLength(min = 1, max = 100) string1: String,
        @ForAll @NumericChars @StringLength(min = 1, max = 100) string2: String,
    ): Boolean {
        val expectedOperand1 = string1.toBigInteger()
        val expectedOperand2 = string2.toBigInteger()

        val expectedResult = expectedOperand1 + expectedOperand2

        return checkRule(string1, string2) { number1, number2 ->
            val expected = BytePerDigit(expectedResult.toString())
            val actual = number1 + number2

            expected == actual
        }
    }

    @Property
    fun `check commutativity`(
        @ForAll @NumericChars @StringLength(min = 0, max = 100) string1: String,
        @ForAll @NumericChars @StringLength(min = 0, max = 100) string2: String,
    ): Boolean = checkRule(string1, string2) { number1, number2 ->
        number1 + number2 == number2 + number1
    }

    @Property
    fun `check associativity`(
        @ForAll @NumericChars @StringLength(min = 0, max = 100) string1: String,
        @ForAll @NumericChars @StringLength(min = 0, max = 100) string2: String,
        @ForAll @NumericChars @StringLength(min = 0, max = 100) string3: String,
    ): Boolean = checkRule(string1, string2, string3) { numbers ->
        val (number1, number2, number3) = numbers
        (number1 + number2) + number3 == number1 + (number2 + number3)
    }

    @Property
    fun `check monotony`(
        @ForAll @NumericChars @StringLength(min = 0, max = 100) string1: String,
        @ForAll @NumericChars @StringLength(min = 0, max = 100) string2: String,
    ): Boolean = checkRule(string1, string2) { number1, number2 ->
        val sum = number1 + number2
        sum >= number1 && sum >= number2
    }

    @Property
    fun `check neutral element`(
        @ForAll @NumericChars @StringLength(min = 0, max = 100) string: String
    ): Boolean {
        val number = BytePerDigit(string)
        val zero = BytePerDigit()

        return number + zero == number
    }
}
