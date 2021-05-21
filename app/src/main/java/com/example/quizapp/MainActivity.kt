package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    companion object {
        var cheatCount = 0
    }

    private lateinit var questionTv: TextView
    private lateinit var trueBtn: Button
    private lateinit var falseBtn: Button
    private lateinit var nextBtn: Button
    private lateinit var showAnswerBtn: Button

    private lateinit var questions: Array<String>
    private lateinit var answers: Array<String>

    private lateinit var userAnswer: Array<Boolean>

    private var i = 0

    private fun init() {
        questionTv = findViewById(R.id.question_text_view)
        trueBtn = findViewById(R.id.true_btn)
        falseBtn = findViewById(R.id.false_btn)
        nextBtn = findViewById(R.id.next_button)
        showAnswerBtn = findViewById(R.id.show_answer_button)
        questions = applicationContext.resources.getStringArray(R.array.questions)
        answers = applicationContext.resources.getStringArray(R.array.answers)
        userAnswer = Array(questions.size) { false }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val database = FirebaseDatabase.getInstance()
        val databaseRef = database.getReference("ghj")





        init()

        questionTv.text = questions[i]

        trueBtn.setOnClickListener {
            doAnswer(true)
        }

        falseBtn.setOnClickListener {
            doAnswer(false)
        }

        nextBtn.setOnClickListener {
            showNextQuestion()
        }

        showAnswerBtn.setOnClickListener { showAnswer() }
    }

    private fun showNextQuestion() {
        if (i + 1 != questions.size) {
            i++
            questionTv.text = questions[i]
            nextBtn.visibility = View.INVISIBLE
        } else {
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("quiz_result", userAnswer)
            startActivity(intent)
            finish()
        }
    }

    private fun doAnswer(answer: Boolean) {
        userAnswer[i] = answer.toString() == answers[i]
        nextBtn.visibility = View.VISIBLE
    }

    private fun showAnswer() {
        cheatCount++
        if (cheatCount > 2) {
            Toast.makeText(this, "You cannot cheat!", Toast.LENGTH_SHORT).show()
            return
        }
        val intent = Intent(this, CheatActivity::class.java)
        intent.putExtra("correct_answer", answers[i])
        startActivity(intent)
    }
}