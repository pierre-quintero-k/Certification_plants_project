package com.example.plantcertification.View

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.plantcertification.R
import com.example.plantcertification.ViewModel.PlantViewModel
import com.example.plantcertification.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {

    // Declaracion de variables
    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PlantViewModel by activityViewModels()
    private var plantId: String? = null

    // Implementacion de funciones
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Recibiendo del primer fragmento
        arguments?.let { bundle ->
            plantId = bundle.getString("plantid")
            Log.d("***LO RECIBO??***", plantId.toString())
            //recibo bien
        }

        // Se recibe ID de un plant para pasarselo a la funcion obtener detalle de internet
        plantId?.let { id ->
            viewModel.getPlantDetailByIdFromInternet(id)
        }

        // Llama la funcion PlantDetail, la observa y se pasan los datos
        viewModel.getPlantDetail().observe(viewLifecycleOwner, Observer {

            Glide.with(binding.imageView2).load(it.imagen).into(binding.imageView2)
            binding.id.text = "ID Planta         : " + it.id
            binding.nombre.text = "Nombre Planta   : " + it.nombre
            binding.tipo.text = "Tipo                   : " + it.tipo
            binding.descripcion.text = "Descripción          : " + it.descripcion


            // Declaracion de variables para enviar correo
            var name = it.nombre
            var code = it.id.toString()

            // Boton para enviar correo utilizando intent.
            binding.btfab.setOnClickListener {

                val intent = Intent(Intent.ACTION_SEND)
                intent.data = Uri.parse("mailto")
                intent.type = "text/plain"

                intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("info@novaera.cl"))
                intent.putExtra(
                    Intent.EXTRA_SUBJECT,
                    "Consulta " + name + "id" + code
                )
                intent.putExtra(
                    Intent.EXTRA_TEXT, "Hola\n" +
                            "Vi la Planta " + name + " de código: " + code + ",\n" +
                            "Me gustaría Comprarla , por favor contacteme a este correo o al siguiente número:___________\n" +
                            "Quedo atento"
                )
                startActivity(intent)
            }
        })

        // Implementacion volver al primer fragmento
        binding.btvolver.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    // funcion para destruir la vista
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
