import net.jqwik.api.Assume
import net.jqwik.api.ForAll
import net.jqwik.api.Property
import net.jqwik.api.constraints.AlphaChars
import net.jqwik.api.constraints.NumericChars
import net.jqwik.api.constraints.StringLength
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.assertFailsWith


private fun String.isNumeric(): Boolean = this.all { it.isDigit() }


class ConstructorTest {
    @Property
    fun `pass on strings of digits`(
        @ForAll @NumericChars @StringLength(min = 0, max = 100) string: String
    ): Unit {
        Assume.that(string.isNumeric())

        assertDoesNotThrow {
            BytePerDigit(string)
        }
    }

    @Property
    fun `throw an exception on strings with non-digits`(
        @ForAll @NumericChars @AlphaChars @StringLength(min = 1, max = 100) string: String
    ): Boolean {
        Assume.that(!string.isNumeric())

        val exception = assertFailsWith<IllegalArgumentException> {
            BytePerDigit(string)
        }

        return exception.message == BytePerDigit.MAGNITUDE_MUST_BE_DIGITS
    }
}
