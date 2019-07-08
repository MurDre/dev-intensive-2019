package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        if (!fullName.isNullOrBlank()) {
            fullName.trim().split(" ").apply {
                return if (this.size > 1) component1() to component2() else component1() to null
            }
        }
        return null to null
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        return if (!firstName.isNullOrBlank()) {
            "${firstName.trim()[0].toUpperCase()}" +
                    if (!lastName.isNullOrBlank()) lastName.trim()[0].toUpperCase().toString() else ""
        } else if (!lastName.isNullOrBlank()) lastName.trim()[0].toUpperCase().toString() else null
    }

    fun transliteration(payload: String, divider: String = " "): String {
        return buildString {
            payload.trim().split(" ").asSequence().forEach { string ->
                string.asSequence().forEach { char ->
                    var res = translit[char.toLowerCase().toString()]
                    if (char.isUpperCase()) res = res?.capitalize()
                    this@buildString.append(res ?: char.toString())
                }
                this.append(divider)
            }
        }.removeSuffix(divider)
    }

    val translit = mapOf(
        Pair("а", "a"),
        Pair("б", "b"),
        Pair("в", "v"),
        Pair("г", "g"),
        Pair("д", "d"),
        Pair("е", "e"),
        Pair("ё", "e"),
        Pair("ж", "zh"),
        Pair("з", "z"),
        Pair("и", "i"),
        Pair("й", "i"),
        Pair("к", "k"),
        Pair("л", "l"),
        Pair("м", "m"),
        Pair("н", "n"),
        Pair("о", "o"),
        Pair("п", "p"),
        Pair("р", "r"),
        Pair("с", "s"),
        Pair("т", "t"),
        Pair("у", "u"),
        Pair("ф", "f"),
        Pair("х", "h"),
        Pair("ц", "c"),
        Pair("ч", "ch"),
        Pair("ш", "sh"),
        Pair("щ", "sh'"),
        Pair("ъ", ""),
        Pair("ы", "i"),
        Pair("ь", ""),
        Pair("э", "e"),
        Pair("ю", "yu"),
        Pair("я", "ya")
    )
}