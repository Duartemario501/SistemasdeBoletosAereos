package com.example.sistemasdeboletosaereos.ui.compra

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sistemasdeboletosaereos.databinding.FragmentCompraVueloBinding
import com.example.sistemasdeboletosaereos.db.VuelosEntity
import com.example.sistemasdeboletosaereos.util.VueloAdapter
import java.text.FieldPosition

class CompraVueloFragment : Fragment() {
    private var _binding: FragmentCompraVueloBinding? = null
    private var vuelos = ArrayList<VuelosEntity>()

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompraVueloBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // PAISES
        val spn = binding.spnPais
        if(spn != null){

            val paises = arrayOf("Guatemala", "Costa Rica", "Mexico")
            val adapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item,paises)
            spn.adapter = adapter

            spn.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    Log.i("SELECT-PAIS", "Pais seleccionado" + paises.get(position))
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }
        }

        // VUELOS DISPONIBLES SEGUN BUSQUEDA
        val rvVuelos = binding.rvVuelosEncontrados
        rvVuelos.setHasFixedSize(true)
        rvVuelos.layoutManager = LinearLayoutManager(requireContext())
        addVuelos()
        val adapter = VueloAdapter(vuelos)
        rvVuelos.adapter = adapter

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addVuelos(){
        vuelos.add(
            VuelosEntity(1, 1,
                "EL SALVADOR", "COSTA RICA",
                "25/05/2023", "05/06/2023")
        )

        vuelos.add(
            VuelosEntity(1, 1,
                "EL SALVADOR", "GUATEMALA",
                "31/05/2023", "05/06/2023")
        )

        vuelos.add(
            VuelosEntity(1, 1,
                "EL SALVADOR", "Mexico",
                "31/05/2023", "05/06/2023")
        )
    }

}