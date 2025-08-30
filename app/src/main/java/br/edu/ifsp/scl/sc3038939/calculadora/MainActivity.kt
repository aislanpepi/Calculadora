package br.edu.ifsp.scl.sc3038939.calculadora

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.sc3038939.calculadora.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val regex = Regex("[+\\-/*]")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val buttons = listOf(
            binding.button0, binding.button1, binding.button2, binding.button3,
            binding.button4, binding.button5, binding.button6, binding.button7,
            binding.button8, binding.button9,
            binding.buttonplus, binding.buttonminus,
            binding.buttonmultiply, binding.buttondivide,
            binding.buttondot
        )
        val operators = listOf(
            binding.buttonplus, binding.buttonminus,
            binding.buttonmultiply, binding.buttondivide
        )

        buttons.forEach { button ->
            button.setOnClickListener {
                if (operators.contains(button)){
                    if(binding.txtinput.text.contains(regex)){
                        Toast.makeText(this,"Permitido apenas um operador", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        binding.txtinput.append(button.text)
                    }
                }
                else{
                    binding.txtinput.append(button.text)
                }
            }
        }

        binding.buttonequals.setOnClickListener {
            calculate()
        }

        binding.buttonClear.setOnClickListener {
            binding.txtinput.text = ""
        }
    }


    fun calculate(){
        val txtView = binding.txtinput.text
        var firstNumber = ""
        var secondNumber = ""
        val match = regex.find(txtView.toString())
        var i = 0
        var validOperation = true

        if(txtView.toString().isEmpty()){
            Toast.makeText(this, "Entrada vazia", Toast.LENGTH_SHORT).show()
            return
        }

        if(match == null){
            Toast.makeText(this,"Nao foi passado operador!",Toast.LENGTH_SHORT).show()
            return
        }

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
                Toast.makeText(this,"Nao foi passado operador!",Toast.LENGTH_SHORT).show()
                binding.txtinput.text = firstNumber
                return
            }
        }

        if(validOperation)
            binding.txtinput.text = result.toString()
        else
            binding.txtinput.text = ""
    }
}
