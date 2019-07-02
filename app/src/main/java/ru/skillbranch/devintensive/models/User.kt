package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.utils.Utils
import java.util.Date;

data class User(
    val id: String,
    var firstName: String?,
    var lastName: String?,
    var avatar: String?,
    var raiting: Int = 0,
    var respect: Int = 0,
    val lastVisit: Date? = null,
    val isOnline: Boolean = false
) {

    var introBit :String

    constructor(id: String, firstName: String?, lastName: String?) : this(
        id = id,
        firstName = firstName,
        lastName = lastName,
        avatar = null
    )

    constructor(id: String) : this(id, "John", "Doe")

    init {
        introBit = getIntro()
        println("It's Alive!!!\n" +
                "${if (lastName==="Doe") "His name is $firstName $lastName" else "And his name is $firstName $lastName!!!"}\n")
    }

    private fun getIntro() ="""
        tu tu ru tuuuu !!!
        tu tu ru tuuuuuuuu ...

        tu tu ru tuuuu !!!
        tu tu ru tuuuuuuuu ...
        ${"\n\n\n"}
        $firstName $lastName


    """.trimIndent()

    fun printMe() = println(
        """
            id: $id
            firstName: $firstName
            lastName: $lastName
            avatar: $avatar
            raiting: $raiting
            respect: $respect
            lastVisit: $lastVisit
            isOnline: $isOnline
        """.trimIndent()
    )

    companion object Factory{
        private var lastId : Int = -1
        fun makeUser(fullName:String?):User{
            lastId++



            val (firstName, lastName) = Utils.parseFullName(fullName)

            return User(id = "$lastId", firstName = firstName, lastName = lastName)
        }


    }
}