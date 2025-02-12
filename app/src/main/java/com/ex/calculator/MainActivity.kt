package com.ex.calculator

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import com.ex.calculator.databinding.ActivityMainBinding

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

        binding.apply {
            binding.main.children.filterIsInstance<Button>().forEach { button ->
                button.setOnClickListener {

                    val buttonText = button.text.toString()
                    when {
                        buttonText.matches(Regex("[0-9]")) -> {
                            if (currentOperator.isEmpty()) {
                                firstNumber += buttonText
                                tvResult.text = firstNumber
                            } else {
                                currentNumber += buttonText
                                tvResult.text = currentNumber
                            }
                        }

                        buttonText.matches((Regex("[+\\-*/]"))) -> {
                            currentNumber = ""
                            if (tvResult.text.toString().isNotEmpty()) {
                                currentOperator = buttonText
                                tvFormula.text = "$firstNumber $currentOperator"
                                tvResult.text = "0"
                            }
                        }

                        buttonText == "=" -> {
                            if (currentNumber.isNotEmpty() && currentOperator.isNotEmpty()) {
                                tvFormula.text = "$firstNumber$currentOperator$currentNumber"
                                result =
                                    evaluateExpression(firstNumber, currentNumber, currentOperator)
                                firstNumber = result
                                tvResult.text = result
                            }
                        }

                        buttonText == "." -> {
                            if (currentOperator.isEmpty()) {
                                if (!firstNumber.contains(".")) {
                                    firstNumber += if (firstNumber.isEmpty()) "0$buttonText"
                                    else buttonText
                                    tvResult.text = firstNumber
                                }
                            } else {
                                if (!currentNumber.contains(".")) {
                                    currentNumber += if (currentNumber.isEmpty()) {
                                        "0$buttonText"
                                    } else buttonText
                                    tvResult.text = currentNumber
                                }
                            }
                        }

                        buttonText == "C" -> {
                            currentNumber = ""
                            firstNumber = ""
                            currentOperator = ""
                            tvResult.text = "0"
                            tvFormula.text = ""
                        }
                        //Melhorar a logica
                        buttonText == "←" -> {
                            if (currentNumber.isNotEmpty()) {
                                currentNumber = currentNumber.dropLast(1)
                                binding.tvResult.text =
                                    if (currentNumber.isEmpty()) "0" else currentNumber

                            } else if (currentOperator.isNotEmpty()) {
                                currentOperator = ""
                                binding.tvResult.text = firstNumber

                            } else if (firstNumber.isNotEmpty()) {
                                    firstNumber = firstNumber.dropLast(1)
                                    binding.tvResult.text =
                                        if (firstNumber.isEmpty()) "0" else firstNumber
                                }
                        }
                    }
                }
            }
        }
    }
    private fun evaluateExpression(
        firstNumber: String,
        currentNumber: String,
        operador: String
    ): String {
        val num1 = firstNumber.toDoubleOrNull() ?: return "Erro"
        val num2 = currentNumber.toDoubleOrNull() ?: return "Erro"
        return when (operador) {
            "+" -> (num1 + num2).toString()
            "-" -> (num1 - num2).toString()
            "*" -> (num1 * num2).toString()
            "/" -> (num1 / num2).toString()
            else -> ""
        }
    }
}