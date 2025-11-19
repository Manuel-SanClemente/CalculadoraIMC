package com.example.calculoimc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import com.google.android.material.appbar.MaterialToolbar
import org.jetbrains.annotations.TestOnly

class ResultImcFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_result_imc, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val boton = view.findViewById<Button>(R.id.recalcular)

        val args = ResultImcFragmentArgs.fromBundle(requireArguments())
        val genero = args.genero
        val altura = args.altura
        val peso = args.peso
        val edad = args.edad

        val titulo = view.findViewById<TextView>(R.id.title_result)
        val contenido = view.findViewById<TextView>(R.id.value_result)
        val descripcion = view.findViewById<TextView>(R.id.description_result)

        // IMC correcto
        val alturaM = altura / 100.0
        val resultado = peso / (alturaM * alturaM)

        contenido.text = String.format("%.1f", resultado)

        when {
            resultado <= 18.5 -> {
                titulo.text = "Bajo peso"
                descripcion.text = "Estás por debajo de la media"
            }
            resultado <= 25.0 -> {
                titulo.text = "Normal"
                descripcion.text = "Estás en la media"
            }
            resultado <= 30.0 -> {
                titulo.text = "Sobrepeso"
                descripcion.text = "Estás por encima de la media"
            }
            else -> {
                titulo.text = "Obesidad"
                descripcion.text = "Menos donuts"
            }
        }

        boton.setOnClickListener {
            view.findNavController()
                .navigate(R.id.action_resultImcFragment_to_imcCalculatorFragment)
        }
    }
}
