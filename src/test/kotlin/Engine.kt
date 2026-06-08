import kotlin.math.sign


fun String.isAllZeros() = this.all { it == '0' }


infix fun String.compareAsNumbersWith(other: String): Int =
    compareValuesBy(
        this.trimStart('0'),
        other.trimStart('0'),
        { it.length }, { it }
    )


infix fun Int.hasSameSignTo(other: Int): Boolean =
    this.sign == other.sign


infix fun Int.hasOppositeSignTo(other: Int): Boolean =
    this.sign * other.sign == -1


fun <TypeExpected> runTest(
    input: String,
    expected: TypeExpected,
    fetchActual: (BytePerDigit) -> TypeExpected
): Boolean {
    val vastNatural = BytePerDigit(input)
    val actual = fetchActual(vastNatural)

    return expected == actual
}


fun checkRule(
    input1: String,
    input2: String,
    rule: (BytePerDigit, BytePerDigit) -> Boolean
): Boolean = rule(
    BytePerDigit(input1),
    BytePerDigit(input2),
)


fun checkRule(
    vararg inputs: String,
    rule: (List<BytePerDigit>) -> Boolean
): Boolean = rule(inputs.map { BytePerDigit(it) })


fun checkRuleSortedDescending(
    input1: String,
    input2: String,
    rule: (BytePerDigit, BytePerDigit) -> Boolean
): Boolean {
    val cmp = input1 compareAsNumbersWith input2

    val (minInput, maxInput) =
        if (cmp > 0) input1 to input2
        else input2 to input1

    return checkRule(minInput, maxInput, rule)
}
