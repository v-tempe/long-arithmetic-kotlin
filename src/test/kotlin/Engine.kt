
fun String.isAllZeros() = this.all { it == '0' }


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
