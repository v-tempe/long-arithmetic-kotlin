import net.jqwik.api.ForAll
import net.jqwik.api.Property
import net.jqwik.api.constraints.NumericChars
import net.jqwik.api.constraints.StringLength


class IterationTest {
    @Property
    fun `test on 'for' loop`(
        @ForAll @NumericChars @StringLength(min = 0, max = 100) string: String
    ): Boolean {
        val expected: List<Byte> = string.trimStart('0')
            .toList().map { it.digitToInt().toByte() }

        return runTest(string, expected) { vastNatural ->
            val actual: MutableList<Byte> = mutableListOf()

            for (digit in vastNatural)
                actual.addLast(digit)

            actual
        }
    }
}
