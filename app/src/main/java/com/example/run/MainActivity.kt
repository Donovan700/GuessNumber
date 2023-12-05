package com.example.run

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random
import android.graphics.Color

class MainActivity : AppCompatActivity() {

    private lateinit var textViewChances: TextView
    private lateinit var editTextGuess: EditText
    private lateinit var buttonGuess: Button
    private lateinit var buttonReset: Button
    private lateinit var textViewResult: TextView

    private var remainingAttempts = 7
    private var numberToGuess = generateRandomNumber()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViews()
    }
    private fun initializeViews() {
        textViewChances = findViewById(R.id.chancesTextView)
        editTextGuess = findViewById(R.id.editTextGuess)
        buttonGuess = findViewById(R.id.checkButton)
        buttonReset = findViewById(R.id.resetButton)
        textViewResult = findViewById(R.id.messagesTextView)

        buttonGuess.setOnClickListener { buttonGuessClick() }
        buttonReset.setOnClickListener { buttonResetClick() }
    }

    private fun generateRandomNumber(): Int {
        return Random.nextInt(1, 101)
    }

    private fun buttonGuessClick() {
        if (remainingAttempts > 0) {
            val userGuess = getUserGuess()

            when {
                userGuess == numberToGuess -> handleCorrectGuess()
                userGuess < numberToGuess -> handleGuessTooLow()
                else -> handleGuessTooHigh()
            }

            if (remainingAttempts == 0) {
                handleOutOfAttempts()
            }

            remainingAttempts--
            updateChancesRemaining()
        } else {
            handleOutOfAttempts()
        }
    }
    private fun getUserGuess(): Int {
        return try {
            editTextGuess.text.toString().toInt()
        } catch (e: NumberFormatException) {
            // Handle invalid input
            textViewResult.text = getString(R.string.enter_valid_number)
            editTextGuess.text.clear()
            -1
        }
    }

    private fun handleCorrectGuess() {
        textViewResult.text = getString(R.string.congratulations)
        textViewResult.setTextColor(Color.GREEN)
        disableCheckButton()
    }

    private fun handleGuessTooLow() {
        textViewResult.text = getString(R.string.too_low)
        editTextGuess.text.clear()
    }

    private fun handleGuessTooHigh() {
        textViewResult.text = getString(R.string.too_high)
        editTextGuess.text.clear()
    }

    private fun handleOutOfAttempts() {
        textViewResult.text = getString(R.string.out_of_attempts, numberToGuess)
        textViewResult.setTextColor(Color.RED)
        disableCheckButton()
    }


    private fun updateChancesRemaining() {
        textViewChances.text = getString(R.string.chances_remaining_template, remainingAttempts)
    }

    private fun disableCheckButton() {
        buttonGuess.isEnabled = false
    }

    private fun enableCheckButton() {
        buttonGuess.isEnabled = true
    }

    private fun buttonResetClick() {
        remainingAttempts = 7
        numberToGuess = generateRandomNumber()
        enableCheckButton()
        textViewResult.setTextColor(Color.BLACK)
        resetGame()
    }

    private fun resetGame() {
        textViewResult.text = getString(R.string.None)
        editTextGuess.text.clear()
        updateChancesRemaining()
    }


}