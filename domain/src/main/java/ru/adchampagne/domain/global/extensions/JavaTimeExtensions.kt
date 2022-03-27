package ru.adchampagne.domain.global.extensions

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.Temporal
import java.util.*

fun Date.toLocalDateTime(): LocalDateTime =
    LocalDateTime.ofInstant(toInstant(), ZoneId.systemDefault())

fun LocalDateTime.toDate(): Date = Date.from(atZone(ZoneId.systemDefault()).toInstant())

fun Temporal.formatByPattern(pattern: String, locale: Locale = Locale.getDefault()): String =
    DateTimeFormatter.ofPattern(pattern, locale).format(this)

// Fixes formatting of named patterns, such as LLLL or EEEE
fun LocalDateTime.formatByPatternCompat(
    pattern: String,
    locale: Locale = Locale.getDefault()
): String = toDate().let(SimpleDateFormat(pattern, locale)::format)

fun LocalDate.formatByPatternCompat(
    pattern: String,
    locale: Locale = Locale.getDefault()
): String = atStartOfDay().formatByPatternCompat(pattern, locale)
