package com.example.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.R

class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView
    private var currentInput: String = ""
    private var operator: String = ""
    private var firstOperand: Double = 0.0
    private var secondOperand: Double = 0.0
    private var isOperatorPressed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Lấy TextView để hiển thị kết quả
        display = findViewById(R.id.display)

        // Gán chức năng cho từng nút
        initializeButtons()
    }

    private fun initializeButtons() {
        val numberButtons = listOf<Button>(
            findViewById(R.id.button0), findViewById(R.id.button1),
            findViewById(R.id.button2), findViewById(R.id.button3),
            findViewById(R.id.button4), findViewById(R.id.button5),
            findViewById(R.id.button6), findViewById(R.id.button7),
            findViewById(R.id.button8), findViewById(R.id.button9)
        )

        for (button in numberButtons) {
            button.setOnClickListener {
                onNumberPressed((it as Button).text.toString())
            }
        }

        val operatorButtons = listOf<Button>(
            findViewById(R.id.buttonAdd), findViewById(R.id.buttonSubtract),
            findViewById(R.id.buttonMultiply), findViewById(R.id.buttonDivide)
        )

        for (button in operatorButtons) {
            button.setOnClickListener {
                onOperatorPressed((it as Button).text.toString())
            }
        }

        findViewById<Button>(R.id.buttonEquals).setOnClickListener { onEqualPressed() }
        findViewById<Button>(R.id.buttonClear).setOnClickListener { onClearPressed() }
        findViewById<Button>(R.id.buttonSign).setOnClickListener { onChangeSignPressed() }
        findViewById<Button>(R.id.buttonDot).setOnClickListener { onDotPressed() }
        findViewById<Button>(R.id.buttonBackspace).setOnClickListener { onBackspacePressed() }
    }

    private fun onNumberPressed(number: String) {
        if (isOperatorPressed) {
            currentInput = number
            isOperatorPressed = false
        } else {
            currentInput += number
        }
        display.text = currentInput
    }

    private fun onOperatorPressed(op: String) {
        if (currentInput.isNotEmpty()) {
            firstOperand = currentInput.toDouble()
            operator = op
            isOperatorPressed = true
        }
    }

    private fun onEqualPressed() {
        if (currentInput.isNotEmpty()) {
            secondOperand = currentInput.toDouble()
            val result = when (operator) {
                "+" -> firstOperand + secondOperand
                "-" -> firstOperand - secondOperand
                "x" -> firstOperand * secondOperand
                "/" -> if (secondOperand != 0.0) firstOperand / secondOperand else "Error"
                else -> ""
            }
            display.text = result.toString()
            currentInput = result.toString()
        }
    }

    private fun onClearPressed() {
        currentInput = ""
        firstOperand = 0.0
        secondOperand = 0.0
        operator = ""
        display.text = "0"
    }

    private fun onChangeSignPressed() {
        if (currentInput.isNotEmpty()) {
            val value = currentInput.toDouble() * -1
            currentInput = value.toString()
            display.text = currentInput
        }
    }

    private fun onDotPressed() {
        if (!currentInput.contains(".")) {
            currentInput += "."
            display.text = currentInput
        }
    }

    private fun onBackspacePressed() {
        if (currentInput.isNotEmpty()) {
            currentInput = currentInput.dropLast(1)
            if (currentInput.isEmpty()) {
                display.text = "0"
            } else {
                display.text = currentInput
            }
        }
    }
}