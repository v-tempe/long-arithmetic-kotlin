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

    override val digitCount: Int = this.digits.size

    override fun iterator(): Iterator<Byte> = this.digits.reversed().iterator()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is VastNatural) return false
        return this.compareTo(other) == 0
    }

    override fun hashCode(): Int {
        return this.digits.fold(1) { acc, digit -> acc * 31 + digit }
    }

    override fun toString(): String {
        if (this.digits.isEmpty()) return "0"
        return this.digits.reversed().joinToString("")
    }
}
