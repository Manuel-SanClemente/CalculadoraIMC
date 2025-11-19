package com.example.calculoimc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.slider.RangeSlider

class ImcCalculatorFragment : Fragment() {
    /* Función para cuando se pida crear la vista. Esta función devuelve la vista */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_imc_calculator, container, false)
    }

    /* Una vez creada la vista, comenzamos */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* Encuetra la toolbar */
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar_calculator)

        /*Creamos una variable que almacenara el genero del usuario, más dos referencias a las tarjetas que referencian a ambos generos*/
        var generoVal = ""
        var masc = view.findViewById<CardView>(R.id.masc_card)
        var fem = view.findViewById<CardView>(R.id.fem_card)

        /*
        Se establecen dos eventos distintos. Una vez seleccionada la tarjeta del genero especifico, se cambia el conetnido del texto
        a masc/fem, y se cambia el color de las dos tarjetas para reflejar la opción seleccionada
        */
        
        masc.setOnClickListener {
           generoVal = "masc"
           masc.setCardBackgroundColor(requireContext().getColor(R.color.bg_buttons))
           fem.setCardBackgroundColor(requireContext().getColor(R.color.black))
        }

        fem.setOnClickListener {
            generoVal = "fem"
            masc.setCardBackgroundColor(requireContext().getColor((R.color.black)))
            fem.setCardBackgroundColor(requireContext().getColor((R.color.bg_buttons)))
        }

        /* Se obtiene la barra y el valor de la altura */
        var alturaVal = view.findViewById<TextView>(R.id.altura_valor)
        val alturaBar = view.findViewById<RangeSlider>(R.id.medidor_altura)

        /*
        Una vez se cambie la barra a través de su uso, se pasa su valor a un string, y se cambia el contenido del texto superior
        al especificado por la barra
        */
        alturaBar.addOnChangeListener { alturaBar, value, fromUse ->
            alturaVal.setText(value.toString())
        }

        var pesoVal = view.findViewById<TextView>(R.id.peso_valor)
        val pesoMin = view.findViewById<FloatingActionButton>(R.id.fab_minus_peso)
        val pesoMas = view.findViewById<FloatingActionButton>(R.id.fab_plus_peso)

        pesoMin.setOnClickListener {
            var valor = pesoVal.text.toString().toInt()
            valor = valor -1
            pesoVal.setText(valor.toString())
        }
        pesoMas.setOnClickListener {
            var valor = pesoVal.text.toString().toInt()
            valor = valor +1
            pesoVal.setText(valor.toString())
        }

        var edadVal = view.findViewById<TextView>(R.id.edad_valor)
        val edadMin = view.findViewById<FloatingActionButton>(R.id.fab_minus_edad)
        val edadMas = view.findViewById<FloatingActionButton>(R.id.fab_plus_edad)

        edadMin.setOnClickListener {
            var valor = edadVal.text.toString().toInt()
            valor = valor-1
            edadVal.setText(valor.toString())
        }
        edadMas.setOnClickListener {
            var valor = edadVal.text.toString().toInt()
            valor = valor+1
            edadVal.setText(valor.toString())
        }


        val button = view.findViewById<Button>(R.id.boton_calcular)
        button.setOnClickListener {
            calcular(view, generoVal, alturaVal, pesoVal, edadVal)
        }
    }

    private fun calcular(view: View, genero: String, altura: TextView, peso: TextView, edad: TextView) {

        // Validación del género
        if (genero.isEmpty()) {
            Toast.makeText(requireContext(), "Selecciona un género", Toast.LENGTH_SHORT).show()
            return
        }

        // Conversión segura
        val a = altura.text.toString().toIntOrNull() ?: 0
        val p = peso.text.toString().toIntOrNull() ?: 0
        val e = edad.text.toString().toIntOrNull() ?: 0

        // Navegación
        val action = ImcCalculatorFragmentDirections
            .actionImcCalculatorFragmentToResultImcFragment(genero, a, p, e)

        view.findNavController().navigate(action)
    }
}
