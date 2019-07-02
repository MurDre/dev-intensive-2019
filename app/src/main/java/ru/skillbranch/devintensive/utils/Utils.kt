package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {

        /*TODO FIX ME*/
        val parts: List<String>? = fullName?.split(" ")

        val firstName = parts?.getOrNull(0)

        val lastName = parts?.getOrNull(1)
        return fix1(firstName) to fix1(lastName)
    }

    fun fix1(line:String?):String?{
        if (!line.isNullOrBlank()) {
            return line
        }
        return null
    }
}