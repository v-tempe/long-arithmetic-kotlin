import net.jqwik.api.ForAll
import net.jqwik.api.Property
import net.jqwik.api.constraints.NumericChars
import net.jqwik.api.constraints.StringLength


class DigitCountTest {
    private fun checkDigitCount(input: String, expected: Int): Boolean {
        val vastNatural = BytePerDigit(input)
        val actual = vastNatural.digitCount

        return expected == actual
    }

    @Property
    fun `return string length without leading zeros`(
        @ForAll @NumericChars @StringLength(min = 0, max = 100) string: String
    ): Boolean = checkDigitCount(
        string, string.trimStart('0').length
    )
}
