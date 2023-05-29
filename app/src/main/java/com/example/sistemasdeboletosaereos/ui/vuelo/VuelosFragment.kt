package com.example.sistemasdeboletosaereos.ui.vuelo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sistemasdeboletosaereos.databinding.FragmentVuelosBinding
import com.example.sistemasdeboletosaereos.db.VuelosEntity
import com.example.sistemasdeboletosaereos.util.VueloAdapter

class VuelosFragment: Fragment() {
    private var _binding: FragmentVuelosBinding? = null
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
        _binding = FragmentVuelosBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val rvVuelos = binding.rvVuelos
        rvVuelos.setHasFixedSize(true)
        rvVuelos.layoutManager = LinearLayoutManager(requireContext())
        addVuelos()
        val adapter = VueloAdapter(vuelos, 2)
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