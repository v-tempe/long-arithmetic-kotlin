import net.jqwik.api.Assume
import net.jqwik.api.ForAll
import net.jqwik.api.Property
import net.jqwik.api.constraints.Chars
import net.jqwik.api.constraints.NumericChars
import net.jqwik.api.constraints.StringLength


private fun String.isAllZeros() = this.all { it == '0' }


class ToStringTest {
    @Property(tries = 100)
    fun `return single zero on zero value`(
        @ForAll @Chars('0') @StringLength(min = 0, max = 20) string: String
    ): Boolean {
        Assume.that(string.isAllZeros())

        val expected = "0"

        val vastNatural = BytePerDigit(string)
        val actual = vastNatural.toString()

        return expected == actual
    }

    @Property
    fun `trim leading zeros on non-zero value`(
        @ForAll @NumericChars @StringLength(min = 1, max = 100) string: String
    ): Boolean {
        Assume.that(!string.isAllZeros())

        val expected = string.trimStart('0')

        val vastNatural = BytePerDigit(string)
        val actual = vastNatural.toString()

        return expected == actual
    }
}
