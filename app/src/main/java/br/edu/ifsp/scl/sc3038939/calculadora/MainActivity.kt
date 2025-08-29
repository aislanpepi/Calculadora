package br.edu.ifsp.scl.sc3038939.calculadora

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.sc3038939.calculadora.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val numberAndOperatorButtons = listOf(
            binding.button0, binding.button1, binding.button2, binding.button3,
            binding.button4, binding.button5, binding.button6, binding.button7,
            binding.button8, binding.button9,
            binding.buttonplus, binding.buttonminus,
            binding.buttonmultiply, binding.buttondivide,
            binding.buttonvirgula
        )

        numberAndOperatorButtons.forEach { button ->
            button.setOnClickListener {
                binding.txtinput.append(button.text)
            }
        }

        binding.buttonequals.setOnClickListener {
            calculate()
        }
    }


    fun calculate(){
        val txtView = binding.txtinput.text
        var firstNumber = ""
        var secondNumber = ""
        val regex = Regex("[+-/*]")
        val match = regex.find(txtView.toString())
        var i = 0
        var validOperation = true

        if(match == null) throw IllegalStateException("Nao foi passado operador!")

        while (txtView[i].toString() != match.value){
            firstNumber += txtView[i]
            i++
        }
        i++

        while (i < txtView.length){
            secondNumber += txtView[i]
            i++
        }

        val result = when(match.value){
            "+" -> firstNumber.toDouble() + secondNumber.toDouble()
            "-" ->  firstNumber.toDouble() - secondNumber.toDouble()
            "*" -> firstNumber.toDouble() * secondNumber.toDouble()
            "/" -> if(secondNumber == "0") {
                        validOperation = false
                        Toast.makeText(this,"Erro ao dividir por zero", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        firstNumber.toDouble() / secondNumber.toDouble()
                    }
            else -> {
                throw Exception("Ocorreu algo")
            }
        }

        if(validOperation)
            binding.txtinput.text = result.toString()
        else
            binding.txtinput.text = ""
    }
}
