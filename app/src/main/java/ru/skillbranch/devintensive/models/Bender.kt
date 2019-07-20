package ru.skillbranch.devintensive.models

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import ru.skillbranch.devintensive.R

class Bender(
    var status: Status = Status.NORMAL,
    var question: Question = Question.NAME,
    view: View,
    private val onEditorActionListener: TextView.OnEditorActionListener? = null
) {
    private var benderIV: ImageView
    private var questionTV: TextView
    private var messageET: EditText
    private var sendBtn: ImageView

    init {
        benderIV = view.findViewById(R.id.iv_bender)
        questionTV = view.findViewById(R.id.tv_text)
        messageET = view.findViewById(R.id.et_message)
        sendBtn = view.findViewById(R.id.iv_send)
        sendBtn.setOnClickListener { listenAnswer(messageET.text.toString()) }
        messageET.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                listenAnswer(messageET.text.toString())
                onEditorActionListener?.onEditorAction(v, actionId, event)
                true
            } else {
                false
            }
        }
        start()
    }

    fun changeBenderStatus(statusColor: Triple<Int, Int, Int>) {
        benderIV.setColorFilter(
            Color.rgb(statusColor.first, statusColor.second, statusColor.third),
            PorterDuff.Mode.MULTIPLY
        )
    }

    fun showQuestion(question: String) {
        questionTV.text = question
    }

    fun askQuestion(): String = when (question) {
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }


    fun start() {
        changeBenderStatus(status.color)
        showQuestion(askQuestion())
    }

    fun listenAnswerAndValid(answer: String): Pair<String, Triple<Int, Int, Int>> {
        return when (question) {
            Question.IDLE -> question.question to status.color
            else -> "${validateAnswer(answer)}\n${question.question}" to status.color
        }
    }

    fun validateAnswer(answer: String): String {
        if (!question.validate(answer)) {
            return ""
        }
        if (question.answer.contains(answer.toLowerCase())) {
            question = question.nextQuestion()
            return "Отлично - ты справился"
        } else {
            return if (status == Status.CRITICAL) {
                resetStatus()
                "Это неправильный ответ. Давай все по новой"
            } else {
                status = status.nextStatus()
                "Это неправильный ответ"
            }
        }
    }

    fun listenAnswer(answer: String) {
        val isTrue = question.validate(answer)
        val status = listenAnswerAndValid(answer)
        changeBenderStatus(status.second)
        messageET.setText("")
        if (isTrue) {
            showQuestion(status.first)
        } else {
            showQuestion(makeErrorMessage())
        }
    }

    fun makeErrorMessage(): String {
        val errorMessage = when (question) {
            Question.NAME -> "Имя должно начинаться с заглавной буквы"
            Question.PROFESSION -> "Профессия должна начинаться со строчной буквы"
            Question.MATERIAL -> "Материал не должен содержать цифр"
            Question.BDAY -> "Год моего рождения должен содержать только цифры"
            Question.SERIAL -> "Серийный номер содержит только цифры, и их 7"
            else -> "На этом все, вопросов больше нет"
        }
        return errorMessage + "\n" + question.question
    }

    private fun resetStatus() {
        status = Status.NORMAL
        question = Question.NAME
    }

    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0));

        fun nextStatus(): Status {
            return if (this.ordinal < values().lastIndex)
                values()[this.ordinal + 1]
            else values()[0]
        }
    }

    enum class Question(val question: String, val answer: List<String>) {
        NAME("Как меня зовут?", listOf("бендер", "bender")) {
            override fun nextQuestion(): Question = PROFESSION
            override fun validate(answer: String): Boolean = answer.trim().firstOrNull()?.isUpperCase() ?: false
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")) {
            override fun nextQuestion(): Question = MATERIAL
            override fun validate(answer: String): Boolean = answer.trim().firstOrNull()?.isLowerCase() ?: false
        },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "iron", "wood", "metal")) {
            override fun nextQuestion(): Question = BDAY
            override fun validate(answer: String): Boolean = answer.trim().contains(Regex("\\d")).not()
        },
        BDAY("Когда меня создали?", listOf("2993")) {
            override fun nextQuestion(): Question = SERIAL
            override fun validate(answer: String): Boolean = answer.trim().contains(Regex("^[0-9]*$"))
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun nextQuestion(): Question = IDLE
            override fun validate(answer: String): Boolean = answer.trim().contains(Regex("^[0-9]{7}$"))
        },
        IDLE("На этом все, вопросов больше нет", listOf()) {
            override fun nextQuestion(): Question = IDLE
            override fun validate(answer: String): Boolean = true
        };

        abstract fun nextQuestion(): Question
        abstract fun validate(answer: String): Boolean
    }
}