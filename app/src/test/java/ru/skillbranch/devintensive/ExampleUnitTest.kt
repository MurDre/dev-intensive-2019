package ru.skillbranch.devintensive

import org.junit.Test

import org.junit.Assert.*
import ru.skillbranch.devintensive.models.User
import java.util.Date

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun test_instance() {
        val user2 = User("2", "John", "Cena")
    }

    @Test
    fun test_factory() {
//        val user = User.makeUser("John Cena")
        val user2 = User.makeUser("John Wick")
        val user3 = user2.copy(id = "2", lastName = "Cena", lastVisit = Date())
        val user4 = User.makeUser("")
        val user5 = User.makeUser(" ")
        val user6 = User.makeUser("12")

        println("$user2 \n $user3")

    }
}
