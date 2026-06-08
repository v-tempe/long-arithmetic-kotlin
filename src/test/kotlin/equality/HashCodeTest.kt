package equality

import checkRule
import net.jqwik.api.ForAll
import net.jqwik.api.Property
import net.jqwik.api.constraints.NumericChars
import net.jqwik.api.constraints.StringLength


class HashCodeTest {
    @Property
    fun `check equals-hashCode consistency`(
        @ForAll @NumericChars @StringLength(min = 0, max = 100) string1: String
    ): Boolean = checkRule(string1, string1) { number1, number2 ->
        val numberEquality = number1 == number2
        assert(numberEquality)
        val hashCodeEquality = number1.hashCode() == number2.hashCode()
        numberEquality == hashCodeEquality
    }
    
    @Property
    fun `check integration with HashSet`(
        @ForAll @NumericChars @StringLength(min = 0, max = 100) string: String
    ): Boolean = checkRule(string, string) { number1, number2 ->
        assert(number1 == number2)
        hashSetOf(number1, number2).size == 1
    }
}
