package com.emdp.domain.model.types

enum class BaseAttributeType(val attributeType: String) {
    HP("hp"),
    ATTACK("attack"),
    DEFENSE("defense"),
    SPECIAL_ATTACK("special-attack"),
    SPECIAL_DEFENSE("special-defense"),
    SPEED("speed"),
    UNKNOWN("unknown");

    companion object {
        fun fromAttributeType(type: String?): BaseAttributeType {
            val key = type?.trim()?.lowercase() ?: return UNKNOWN
            return entries.firstOrNull { it.attributeType == key } ?: UNKNOWN
        }
    }

    fun displayName(): String = attributeType.toTitleCase()

    private fun String.toTitleCase(): String =
        trim()
            .split(Regex("[\\s_-]+"))
            .filter { it.isNotEmpty() }
            .joinToString(" ") { part ->
                part.replaceFirstChar { firstChar ->
                    if (firstChar.isLowerCase()) firstChar.titlecase() else firstChar.toString()
                }
            }
}