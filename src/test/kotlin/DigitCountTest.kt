import net.jqwik.api.ForAll
import net.jqwik.api.Property
import net.jqwik.api.constraints.NumericChars
import net.jqwik.api.constraints.StringLength


class DigitCountTest {
    @Property
    fun `return string length without leading zeros`(
        @ForAll @NumericChars @StringLength(min = 0, max = 100) string: String
    ): Boolean = runTest(
        string, string.trimStart('0').length
    ) { it.digitCount }
}
