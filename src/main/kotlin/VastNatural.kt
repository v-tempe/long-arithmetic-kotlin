interface VastNatural : Iterable<Byte>, Comparable<VastNatural> {
    val digitCount: Int

    override fun compareTo(other: VastNatural): Int {
        val lengthComparison = compareValuesBy(
            this, other
        ) { it.digitCount }
        if (lengthComparison != 0) return lengthComparison

        return this.zip(other) { digitThis, digitOther ->
                compareValues(digitThis, digitOther)
            } .firstOrNull { it != 0 } ?: 0
    }

    override fun equals(other: Any?): Boolean

    override fun hashCode(): Int

    operator fun plus(other: VastNatural): VastNatural

    override fun toString(): String
}
