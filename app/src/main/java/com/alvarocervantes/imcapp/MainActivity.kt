package com.alvarocervantes.imcapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencias de la UI
        val etPeso = findViewById<EditText>(R.id.et_peso)
        val etAltura = findViewById<EditText>(R.id.et_altura)
        val rgGenero = findViewById<RadioGroup>(R.id.rg_genero)
        val btnCalcular = findViewById<Button>(R.id.btn_calcular)
        val tvIMC = findViewById<TextView>(R.id.tv_imc)       // Nuevo TextView para mostrar el número del IMC
        val tvEstado = findViewById<TextView>(R.id.tv_estado) // Nuevo TextView para mostrar el estado (Normal, Obesidad, etc.)

        // Evento del botón calcular
        btnCalcular.setOnClickListener {
            val peso = etPeso.text.toString().toDoubleOrNull()
            val altura = etAltura.text.toString().toDoubleOrNull()

            // Validar entradas
            if (peso == null || altura == null || rgGenero.checkedRadioButtonId == -1) {
                Toast.makeText(this, getString(R.string.toast_error), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Cálculo del IMC
            val alturaMetros = altura / 100
            val imc = peso / (alturaMetros * alturaMetros)
            val generoSeleccionado = when (rgGenero.checkedRadioButtonId) {
                R.id.rb_hombre -> "Hombre"
                R.id.rb_mujer -> "Mujer"
                else -> null
            }

            // Determinar estado del IMC
            val estado = calcularEstadoIMC(imc, generoSeleccionado!!)

            // Actualizar la UI
            tvIMC.text = String.format("%.2f", imc) // Mostrar el IMC en grande
            tvEstado.text = estado                  // Mostrar el estado en el rectángulo
        }
    }

    // Método para calcular el estado del IMC basado en el género
    private fun calcularEstadoIMC(imc: Double, genero: String): String {
        return if (genero == "Hombre") {
            when {
                imc < 18.5 -> "Peso inferior al normal"
                imc in 18.5..24.9 -> "Normal"
                imc in 25.0..29.9 -> "Sobrepeso"
                else -> "Obesidad"
            }
        } else {
            when {
                imc < 18.5 -> "Peso inferior al normal"
                imc in 18.5..23.9 -> "Normal"
                imc in 24.0..28.9 -> "Sobrepeso"
                else -> "Obesidad"
            }
        }
    }
}
