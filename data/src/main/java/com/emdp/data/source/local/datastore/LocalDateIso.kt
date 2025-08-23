package com.emdp.data.source.local.datastore

import java.time.LocalDate
import java.time.format.DateTimeFormatter

internal object LocalDateIso {
    private val ISO_LOCAL_DATE_FORMATTER: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE

    fun toIso(date: LocalDate): String = date.format(ISO_LOCAL_DATE_FORMATTER)

    fun parseOrNull(value: String?): LocalDate? =
        value?.let {
            runCatching {
                LocalDate.parse(it, ISO_LOCAL_DATE_FORMATTER)
            }.getOrNull()
        }
}