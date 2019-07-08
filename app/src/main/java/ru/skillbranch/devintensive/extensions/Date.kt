package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.abs

const val DEFAULT_DATE_FORMAT = "HH:mm:ss dd.MM.yy"

fun Date.format(format: String = DEFAULT_DATE_FORMAT): String {
    return SimpleDateFormat(format, Locale("ru")).format(this)
}

fun Date.add(value: Int, timeUnit: TimeUnits): Date {
    return this.apply { time += (value * timeUnit.millis) }
}

fun Date.humanizeDiff(date: Date = Date()): String {
    val diff = date.time - this.time
    val isNegative = diff < 0

    fun suffixTime(unit: TimeUnits, value: Int): String {
        return if (!isNegative) "${unit.plural(value)} назад" else "через ${unit.plural(value)}"
    }

    return when (val time = abs(diff)) {
        in 0..TimeUnits.SECOND.millis -> "только что"
        in TimeUnits.SECOND.millis..TimeUnits.SECOND.millis * 45 -> {
            if (!isNegative) "несколько секунд назад" else "через несколько секунд"
        }
        in TimeUnits.SECOND.millis * 45..TimeUnits.SECOND.millis * 75 -> {
            if (!isNegative) "минуту назад" else "через минуту"
        }
        in TimeUnits.SECOND.millis * 75..TimeUnits.MINUTE.millis * 45 -> {
            suffixTime(TimeUnits.MINUTE, (time / TimeUnits.MINUTE.millis).toInt())
        }
        in TimeUnits.MINUTE.millis * 45..TimeUnits.MINUTE.millis * 75 -> {
            if (!isNegative) "час назад" else "через час"
        }
        in TimeUnits.MINUTE.millis * 75..TimeUnits.HOUR.millis * 22 -> {
            suffixTime(TimeUnits.HOUR, (time / TimeUnits.HOUR.millis).toInt())
        }
        in TimeUnits.HOUR.millis * 22..TimeUnits.HOUR.millis * 26 -> {
            if (!isNegative) "день назад" else "через день"
        }
        in TimeUnits.HOUR.millis * 26..TimeUnits.DAY.millis * 360 -> {
            suffixTime(TimeUnits.DAY, (time / TimeUnits.DAY.millis).toInt())
        }
        else -> if (!isNegative) "более года назад" else "более чем через год"
    }
}

enum class PluralUnits {
    FEW, ONE, MANY
}

enum class TimeUnits(
    val millis: Long
) {
    SECOND(1000),
    MINUTE(SECOND.millis * 60),
    HOUR(MINUTE.millis * 60),
    DAY(HOUR.millis * 24);

    fun plural(value: Int): String {
        var dif = value
        while (dif >= 20) {
            if (dif > 100) {
                dif -= 100
            } else {
                dif = value % 10
            }
        }

        return when (dif) {
            1 -> "$value ${suffixTimeUnit[this]?.get(PluralUnits.ONE)}"
            in 2..4 -> "$value ${suffixTimeUnit[this]?.get(PluralUnits.FEW)}"
            else -> "$value ${suffixTimeUnit[this]?.get(PluralUnits.MANY)}"
        }
    }
}

private val suffixTimeUnit: Map<TimeUnits, Map<PluralUnits, String>> = mapOf(
    TimeUnits.SECOND to mapOf(
        PluralUnits.FEW to "секунды",
        PluralUnits.ONE to "секунду",
        PluralUnits.MANY to "секунд"
    ),
    TimeUnits.MINUTE to mapOf(
        PluralUnits.FEW to "минуты",
        PluralUnits.ONE to "минуту",
        PluralUnits.MANY to "минут"
    ),
    TimeUnits.HOUR to mapOf(
        PluralUnits.FEW to "часа",
        PluralUnits.ONE to "час",
        PluralUnits.MANY to "часов"
    ),
    TimeUnits.DAY to mapOf(
        PluralUnits.FEW to "дня",
        PluralUnits.ONE to "день",
        PluralUnits.MANY to "дней"
    )
)