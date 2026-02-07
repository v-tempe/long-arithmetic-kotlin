
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
): Boolean {
    val number1 = BytePerDigit(input1)
    val number2 = BytePerDigit(input2)

    return rule(number1, number2)
}
