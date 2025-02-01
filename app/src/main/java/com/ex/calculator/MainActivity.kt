package com.ex.calculator

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ex.calculator.databinding.ActivityMainBinding
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var firstNumber = ""
    private var currentNumber = ""
    private var currentOperator = ""
    private var result = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.tvFormula.text = ""
        binding.tvResult.text = "0"

        setNumberButtonListeners()
        setOperatorButtonListeners()
        setSpecialButtonListeners()
    }

    private fun setNumberButtonListeners() {
        val numberButtons = listOf(
            binding.zero, binding.one, binding.two, binding.three,
            binding.four, binding.five, binding.six,
            binding.seven, binding.eight, binding.nine
        )

        for (button in numberButtons) {
            button.setOnClickListener {
                val number = (it as MaterialButton).text.toString()
                currentNumber += number
                binding.tvFormula.text = "$firstNumber $currentOperator $currentNumber"
            }
        }
    }

    private fun setOperatorButtonListeners() {
        val operators = mapOf(
            binding.plus to "+",
            binding.minus to "-",
            binding.multiply to "×",
            binding.divide to "÷"
        )
        for ((button, symbol) in operators) {
            button.setOnClickListener {
                if (currentNumber.isNotEmpty()) {
                    firstNumber = currentNumber
                    currentOperator = symbol
                    currentNumber = ""
                    binding.tvFormula.text = "$firstNumber $currentOperator"
                }
            }
        }
    }

    private fun setSpecialButtonListeners() {
        binding.clear.setOnClickListener {
            firstNumber = ""
            currentNumber = ""
            currentOperator = ""
            result = ""
            binding.tvFormula.text = ""
            binding.tvResult.text = "0"
        }
        binding.equals.setOnClickListener {
            if (firstNumber.isNotEmpty() && currentNumber.isNotEmpty()) {
                val result = calculate(firstNumber.toDouble(), currentNumber.toDouble(), currentOperator)
                binding.tvFormula.text = "$firstNumber $currentOperator $currentNumber ="
                binding.tvResult.text = result.toString()
            }
        }
    }

    private fun calculate(num1: Double, num2: Double, operator: String): Double {
        return when (operator) {
            "+" -> num1 + num2
            "-" -> num1 - num2
            "×" -> num1 * num2
            "÷" -> {
                if (num2 != 0.0) num1 / num2 else {
                    binding.tvResult.text = "Erro: Divisão por zero"
                    return Double.NaN
                }
            }
            else -> 0.0
        }
    }
}