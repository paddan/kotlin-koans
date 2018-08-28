package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int): Comparable<MyDate> {
    override fun compareTo(other: MyDate) = when {
        year != other.year -> year - other.year
        month != other.month -> month - other.month
        else -> dayOfMonth - other.dayOfMonth
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)
operator fun MyDate.plus(interval: TimeInterval): MyDate = addTimeIntervals(interval, 1)
operator fun MyDate.plus(timeIntervals: RepeatedTimeInterval) = addTimeIntervals(timeIntervals.ti, timeIntervals.n)
operator fun TimeInterval.times(number: Int) = RepeatedTimeInterval(this, number)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

class DateRange(override val start: MyDate, override val endInclusive: MyDate): ClosedRange<MyDate>, Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> {
        return DateIterator(this)
    }

    override fun contains(d: MyDate): Boolean {
        return d >= start && d <= endInclusive
    }
}

class DateIterator(val range: DateRange) : Iterator<MyDate> {
    var current: MyDate = range.start

    override fun hasNext(): Boolean {
        return range.contains(current)
    }

    override fun next(): MyDate {
        val nextDate = current
        current = current.nextDay()
        return nextDate
    }

}

class RepeatedTimeInterval(val ti: TimeInterval, val n: Int)