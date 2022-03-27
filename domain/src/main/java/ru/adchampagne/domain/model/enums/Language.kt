package ru.adchampagne.domain.model.enums

enum class Language(
    val id: Int,
    val tag: String,
    val localizedName: String
) {
    RU(1, "ru", "Русский"),
    EN(2, "en", "English");

    companion object {
        operator fun get(tag: String) = values().find { it.tag == tag } ?: RU
    }
}