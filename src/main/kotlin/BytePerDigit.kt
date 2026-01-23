private fun List<Byte>.trimTrailingZeros(): List<Byte> =
    this.dropLastWhile { it == 0.toByte() }


class BytePerDigit(magnitude: String = "") : VastNatural {
    companion object {
        const val MAGNITUDE_MUST_BE_DIGITS = "'magnitude' must be a string of digits"
    }

    private val digits: List<Byte>

    init {
        require(magnitude.all { it.isDigit() }) { MAGNITUDE_MUST_BE_DIGITS }
        this.digits =
            magnitude
                .reversed()
                .map { it.digitToInt().toByte() }
                .trimTrailingZeros()
    }
}
