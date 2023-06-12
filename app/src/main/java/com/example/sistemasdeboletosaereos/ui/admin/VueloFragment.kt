package com.example.sistemasdeboletosaereos.ui.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sistemasdeboletosaereos.databinding.FragmentVuelosBinding
import com.example.sistemasdeboletosaereos.db.DBHelper
import com.example.sistemasdeboletosaereos.db.VuelosEntity
import com.example.sistemasdeboletosaereos.util.VueloAdapter

class VueloFragment : Fragment() {
    private var _binding : FragmentVuelosBinding? = null
    private val binding get() = _binding!!
    private var data = ArrayList<VuelosEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentVuelosBinding.inflate(inflater, container, false)
        val db = DBHelper(requireContext())

        val rvVuelos = binding.rvVuelos
        rvVuelos.setHasFixedSize(true)
        rvVuelos.layoutManager = LinearLayoutManager(requireContext())

        val vuelosCursor = db.getVuelos()
        if(vuelosCursor?.moveToFirst()== true){
            do {
                var vuelo: VuelosEntity = VuelosEntity(
                    vuelosCursor.getString(0), vuelosCursor.getString(1), vuelosCursor.getString(2),
                    vuelosCursor.getString(3), vuelosCursor.getString(4), vuelosCursor.getString(5),
                    vuelosCursor.getString(6), vuelosCursor.getString(7), vuelosCursor.getString(8),
                    vuelosCursor.getString(9), vuelosCursor.getString(10), vuelosCursor.getString(11),
                    vuelosCursor.getString(12), vuelosCursor.getString(13)
                )
                data.add(vuelo)
            }while (vuelosCursor.moveToNext())

        }
        val adapter = VueloAdapter(data, 3)
        rvVuelos.adapter = adapter

        return binding.root
    }
}