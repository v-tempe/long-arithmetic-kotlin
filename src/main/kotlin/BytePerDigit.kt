private fun List<Byte>.trimTrailingZeros(): List<Byte> =
    this.dropLastWhile { it == 0.toByte() }


private const val BASE = 10


class BytePerDigit private constructor(magnitude: List<Byte>) : VastNatural {
    private val digits: List<Byte> = magnitude.trimTrailingZeros()

    companion object {
        private const val MAGNITUDE_MUST_BE_DIGITS = "'magnitude' must be a string of digits"

        operator fun invoke(magnitude: String = ""): BytePerDigit {
            require(magnitude.all { it.isDigit() }) { "$MAGNITUDE_MUST_BE_DIGITS, got $magnitude" }
            return BytePerDigit(
                magnitude
                    .reversed()
                    .map { it.digitToInt().toByte() }
            )
        }
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

    operator fun plus(other: BytePerDigit): BytePerDigit {
        val maxSize = maxOf(this.digits.size, other.digits.size)
        var carry: Byte = 0
        val result = (0 until maxSize).map { i ->
            val resultAddition: Byte = listOf(
                this.digits.getOrNull(i) ?: 0,
                other.digits.getOrNull(i) ?: 0,
                carry
            ).sum().toByte()
            carry = (resultAddition / BASE).toByte()
            val nextDigit = (resultAddition % BASE).toByte()
            nextDigit
        } + listOf(carry)
        return BytePerDigit(result)
    }

    override operator fun plus(other: VastNatural): VastNatural {
        return this + BytePerDigit(other.joinToString(""))
    }

    override fun toString(): String {
        if (this.digits.isEmpty()) return "0"
        return this.digits.reversed().joinToString("")
    }
}
