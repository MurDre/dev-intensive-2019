package ru.skillbranch.devintensive

import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ru.skillbranch.devintensive.extensions.hideKeyboard
import ru.skillbranch.devintensive.models.Bender

class MainActivity : AppCompatActivity(), TextView.OnEditorActionListener {
    companion object {
        const val BENDER_STATUS = "bender.status"
        const val BENDER_QUESTION_NAME = "bender.question.name"
    }

    lateinit var bender: Bender

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val status = savedInstanceState?.getString(BENDER_STATUS) ?: Bender.Status.NORMAL.name
        val question = savedInstanceState?.getString(BENDER_QUESTION_NAME) ?: Bender.Question.NAME.name

        bender = Bender(Bender.Status.valueOf(status), Bender.Question.valueOf(question), findViewById(R.id.bender_view), this)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putString(BENDER_STATUS, bender.status.name)
        outState?.putString(BENDER_QUESTION_NAME, bender.question.name)
        super.onSaveInstanceState(outState)
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        return if (actionId == EditorInfo.IME_ACTION_DONE) {
            hideKeyboard()
            true
        } else {
            false
        }
    }
}
