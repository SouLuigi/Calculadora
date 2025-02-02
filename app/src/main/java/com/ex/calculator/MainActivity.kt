package com.ex.calculator

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ex.calculator.databinding.ActivityMainBinding
import com.google.android.material.button.MaterialButton
import androidx.core.view.children
class MainActivity : AppCompatActivity() {

    //binding
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
        // get all buttons
        binding.apply {
           main.children.filterIsInstance<Button>().forEach { button ->
               button.setOnClickListener{
                   //get clicked button text
                   val buttonText = button.text.toString()
                   when{
                       buttonText.matches(Regex("[0-9]"))->{
                           if(currentOperator.isEmpty()){
                               firstNumber += buttonText
                               tvResult.text = firstNumber
                           }else{
                               currentNumber += buttonText
                               tvResult.text = currentNumber
                           }
                       }
                       buttonText.matches((Regex("[+\\-*/]"))->{
                           currentNumber = ""
                           if (tvResult.text.toString().isNotEmpty())
                           {
                               currentOperator = buttonText
                               tvResult.text = "0"
                           }
                       }
                   }
               }
           }
        }
    }
}