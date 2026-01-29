fun <TypeExpected> runTest(
    input: String,
    expected: TypeExpected,
    fetchActual: (BytePerDigit) -> TypeExpected
): Boolean {
    val vastNatural = BytePerDigit(input)
    val actual = fetchActual(vastNatural)

    return expected == actual
}
